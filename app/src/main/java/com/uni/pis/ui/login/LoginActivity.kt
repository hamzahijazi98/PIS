package com.uni.pis.ui.login


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.facebook.*
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import com.uni.pis.Data.UserData.UserDataGoogle
import com.uni.pis.R
import com.uni.pis.homefrags.MainActivity
import com.uni.pis.ui.SignUp
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private var mAuth: FirebaseAuth? = null
    private var AuthStateListener: FirebaseAuth.AuthStateListener? = null
    private var mCallbackmanger: CallbackManager?=null
    private var AccessTokenTracker: AccessTokenTracker?=null
    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()
        FacebookSdk.sdkInitialize(this)

        val account: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(this)

        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.et_password)
        val login = findViewById<Button>(R.id.login)
        val loading = findViewById<ProgressBar>(R.id.loading)
        val signup = findViewById<Button>(R.id.btn_signup)
        val tv_recovery=findViewById<TextView>(R.id.tv_forgetPassword)
        val  cb_rememberme=findViewById<CheckBox>(R.id.cb_rememberme)
        var sp= getSharedPreferences("myPrefs", Context.MODE_PRIVATE)

        loginViewModel = ViewModelProviders.of(this, LoginViewModelFactory()).get(LoginViewModel::class.java)
        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer
            // disable login button unless both username / password is valid
            login.isEnabled = loginState.isDataValid
            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })
        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer
            loading.visibility = View.GONE
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                updateUiWithUser(loginResult.success)
            }
            setResult(Activity.RESULT_OK)
           finish()

        })
        username.afterTextChanged {
            loginViewModel.loginDataChanged(
                username.text.toString(),
                password.text.toString()
            )
        }
        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
                )
            }


            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                            username.text.toString(),
                            password.text.toString(),
                            cb_rememberme?.isChecked!!,
                            sp
                        )
                }
                false
            }
            if (!username.text.isNullOrBlank()||!password.text.isNullOrBlank())
            {
                loading.visibility = View.VISIBLE

                loginViewModel.login(username.text.toString(), password.text.toString(),false,sp)

                loading.visibility = View.INVISIBLE

            }

            login.setOnClickListener {
                var save =cb_rememberme.isChecked
                loading.visibility = View.VISIBLE

                loginViewModel.login(username.text.toString(), password.text.toString(),save,sp)

                loading.visibility = View.INVISIBLE
            }
            signup.setOnClickListener {

                loading.visibility = View.VISIBLE
                intent = Intent(this@LoginActivity, SignUp::class.java)
                startActivity(intent)
                loading.visibility = View.INVISIBLE
            }
            tv_recovery.setOnClickListener {
                loading.visibility = View.VISIBLE
                intent = Intent(this@LoginActivity, ForgetPassword::class.java)
                if (username.text.toString()!="")
                    intent.putExtra("email",username.text.toString())
                startActivity(intent)
                loading.visibility = View.INVISIBLE

            }
        }
        btn_signGoogle.setOnClickListener {
            signInWithGoogle()
        }

        mCallbackmanger = CallbackManager.Factory.create()
        btn_signFacebook.registerCallback(mCallbackmanger, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {
                handleFacebookToken(result!!.accessToken)
            }
            override fun onCancel() {
                Toast.makeText(applicationContext,"Cancelled ",Toast.LENGTH_SHORT).show()
            }
            override fun onError(error: FacebookException?) {
                Toast.makeText(applicationContext, error!!.message,Toast.LENGTH_SHORT).show()
            }
        })
        btn_signFacebook.setReadPermissions("email","public_profile")
        AuthStateListener=(FirebaseAuth.AuthStateListener { p0 ->
            val user= p0.currentUser
            if(user!=null)
                updateUIFacebook(user)
            else
                updateUIFacebook(null)
        })
        AccessTokenTracker=(object : AccessTokenTracker() {
            override fun onCurrentAccessTokenChanged(oldAccessToken: AccessToken?, currentAccessToken: AccessToken?) {
                if(currentAccessToken ==null)
                    mAuth!!.signOut()
            }
        })
    }
    private fun updateUI(currentUser: GoogleSignInAccount?) {
        if(currentUser != null) {
           var userData: UserDataGoogle = UserDataGoogle(currentUser.displayName.toString(),"",currentUser.email.toString(),
               "","","","",currentUser.photoUrl.toString())
            val intent=Intent(this,SignUp::class.java)
            val bundle = Bundle()
            val parcel = userData
            bundle.putParcelable("userinformation", parcel)
            intent.putExtra("Bundle", bundle)
            startActivity(intent)
        }
        else
            Toast.makeText(this,"Already Signed IN",Toast.LENGTH_SHORT).show()
    }
    private fun handleFacebookToken(Token:AccessToken){
        val AuthCredential= FacebookAuthProvider.getCredential(Token.token)
        mAuth!!.signInWithCredential(AuthCredential).addOnCompleteListener(object : OnCompleteListener<AuthResult> {
            override fun onComplete(p0: Task<AuthResult>) {
                if(p0.isSuccessful)
                {
                    val firebaseuser= mAuth!!.currentUser
                    updateUIFacebook(firebaseuser)
                }
                else
                { Toast.makeText(applicationContext,"Falied ",Toast.LENGTH_LONG).show()
                }
            }
        })

    }
    private fun updateUIFacebook(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            username.setText(currentUser.email)
        } else {
            username.setText("")
        }
    }
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        AuthStateListener?.let { mAuth!!.addAuthStateListener(it) }

    }
    override fun onStop() {
        super.onStop()
        if(AuthStateListener!=null){
            mAuth!!.removeAuthStateListener(AuthStateListener!!)
        }
    }

    private fun signInWithGoogle() {
        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        mAuth = FirebaseAuth.getInstance()
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }
    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
        val credential: AuthCredential =
            GoogleAuthProvider.getCredential(account!!.idToken, null)
        mAuth!!.signInWithCredential(credential).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "onComplete: Success")
                    val user: FirebaseUser? = mAuth!!.currentUser
                    this.updateUI(account)
                } else {
                    Log.d(TAG, "onComplete: Failure: ", task.exception)
                    updateUI(null)
                }
            }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    mCallbackmanger!!.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> =
                GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)
            }
            catch (e: ApiException) {
                Log.d(TAG, "onActivityResult: Sign in FAILED: ", e)
            }
        }

    }
    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome)
        val displayName = model.displayName
        // TODO : initiate successful logged in experience
        intent = Intent(this, MainActivity::class.java)
       startActivity(intent)
        Toast.makeText(
            applicationContext,
            "$welcome $displayName",
            Toast.LENGTH_LONG
        ).show()
    }
    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
    companion object {
        private const val TAG = "GoogleSignInActivity"
        private const val RC_SIGN_IN = 9001
    }
}
/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })

}
