package com.uni.pis.ui.login


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toFile
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import com.uni.pis.BackgroundWorker
import com.uni.pis.Data.UserData.UserDataGoogle
import com.uni.pis.R
import com.uni.pis.homefrags.MainActivity
import com.uni.pis.ui.SignUp
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.net.MalformedURLException
import java.net.URL
import java.util.*


class LoginActivity : AppCompatActivity(),BackgroundWorker.MyCallback {

    private lateinit var loginViewModel: LoginViewModel
    private var mAuth: FirebaseAuth? = null
    private lateinit var sp: SharedPreferences
    var callbackManager: CallbackManager? = null
    var fname: String = ""
    var lname: String = ""
    var email: String = ""
    var gender: String = ""
    var birthday: String = ""
    var id: String = ""
    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        sp = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.et_password)
        val login = findViewById<Button>(R.id.login)
        val loading = findViewById<ProgressBar>(R.id.loading)
        val signup = findViewById<Button>(R.id.btn_signup)
        val tv_recovery = findViewById<TextView>(R.id.tv_forgetPassword)
        val cb_rememberme = findViewById<CheckBox>(R.id.cb_rememberme)


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
            if (!username.text.isNullOrBlank() || !password.text.isNullOrBlank()) {
                loading.visibility = View.VISIBLE

                loginViewModel.login(username.text.toString(), password.text.toString(), false, sp)

                loading.visibility = View.INVISIBLE

            }
            login.setOnClickListener {
                var save = cb_rememberme.isChecked
                loading.visibility = View.VISIBLE

                loginViewModel.login(username.text.toString(), password.text.toString(), save, sp)

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
                if (username.text.toString() != "")
                    intent.putExtra("email", username.text.toString())
                startActivity(intent)
                loading.visibility = View.INVISIBLE

            } }
        btn_signGoogle.setOnClickListener {
            signInWithGoogle()
        }


        callbackManager = CallbackManager.Factory.create()
        mAuth = FirebaseAuth.getInstance()
        login_button.setReadPermissions(Arrays.asList("public_profile, email, user_birthday, user_friends"))
        login_button.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                handleFacebookAccessToken(loginResult.accessToken)
                loginResult.accessToken.userId
                val request: GraphRequest = GraphRequest.newMeRequest(loginResult.accessToken) { obj:JSONObject, response ->
                    try {
                        val profile = Profile.getCurrentProfile()
                        fname=profile.firstName
                        lname=profile.lastName
                        val image =profile.getProfilePictureUri(50,50).toString()
                        email = obj.getString("email")
                val user = mAuth!!.currentUser
                var data = BackgroundWorker(this@LoginActivity)
                data.execute("signup", fname, lname, "update your account", "update your account", email,"update your account", user!!.uid, "update your account",image)
                sp = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
                var editor= sp.edit()
                editor.putString("name", email)
                editor.putString("password", user.uid)
                editor.apply()
                updateUIFacebook(user)
                    }
                    catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
                val parameters = Bundle()
                parameters.putString("fields", "first_name,last_name,email,id")
                request.parameters = parameters
                request.executeAsync()
            }
            override fun onCancel() {
                Log.d("FaceBook Cancelled Login", "facebook:onCancel")
            }
            override fun onError(error: FacebookException) {
                Log.d("FaceBook Error Login", "facebook:onError", error)
            }
        })



    }
    private fun updateUI(currentUser: GoogleSignInAccount?) {
        if (currentUser != null) {
            var userData: UserDataGoogle = UserDataGoogle(
                currentUser.givenName.toString(), currentUser.familyName.toString(), currentUser.email.toString(),
                "", "", "", "", currentUser.photoUrl.toString()
            )
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    private fun signInWithGoogle() {
        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .requestProfile()
                .build()
        val mGoogleSignInClient = GoogleSignIn.getClient(application.applicationContext, gso)
        mAuth = FirebaseAuth.getInstance()
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }
    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
        val credential: AuthCredential = GoogleAuthProvider.getCredential(account!!.idToken, null)
        mAuth!!.signOut()
        mAuth!!.signInWithCredential(credential).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "onComplete: Success")
                val user: FirebaseUser? = mAuth!!.currentUser
                var data = BackgroundWorker(this)
                data.execute("signup", account.givenName, account.familyName, "gender", "update your account", account.email, "update your account", user!!.uid, "update your account", account.photoUrl.toString())
                sp = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
                var editor = sp.edit()
                editor.putString("name", account.email)
                editor.putString("password", user.uid)
                editor.apply()
                this.updateUI(account)
            } else {
                Log.d(TAG, "onComplete: Failure: ", task.exception)
                updateUI(null)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager?.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> =
                GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
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
    override fun onResult(result: String?) {

    }
    private fun handleFacebookAccessToken(token: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(token.token)
        mAuth!!.signInWithCredential(credential).addOnCompleteListener(this) { task ->
            if (!task.isSuccessful) {
                Toast.makeText(baseContext, "Successfully.", Toast.LENGTH_SHORT).show()
            }
            else {
                updateUIFacebook(null)
            }

        }
    }
    fun updateUIFacebook(user: FirebaseUser?) {
        if (user != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
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
}

