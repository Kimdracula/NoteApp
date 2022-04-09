package com.my.notes.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.button.MaterialButton
import com.my.notes.R
import com.my.notes.databinding.StartFragmentBinding

class StartFragment : Fragment() {

    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var butContinue: MaterialButton
    private lateinit var buttonSignOut: MaterialButton
    private lateinit var buttonSignIn: SignInButton
    private var _binding: StartFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = StartFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initGoogleSign()
        enableSign()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initGoogleSign() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
    }

    private fun initView() {

        buttonSignIn = binding.signInButton
        butContinue = binding.buttonContinue
        buttonSignOut = binding.signOutButton
        buttonSignIn.setOnClickListener { signIn() }
        buttonSignOut.setOnClickListener { signOut() }
        butContinue.setOnClickListener {
            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_container, RecycleListFragment())
            fragmentTransaction.addToBackStack("")
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            fragmentTransaction.commit()
        }
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun signOut() {
        mGoogleSignInClient.signOut().addOnSuccessListener(requireActivity()) {
            updateUI("", "")
            setAvatar(null)
            enableSign()
        }
    }

    override fun onStart() {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(requireContext())
        if (account != null) {
            disableSign()
            updateUI(account.displayName, account.email)
            setAvatar(account.photoUrl)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            completedTask.getResult(ApiException::class.java)
            val acct = GoogleSignIn.getLastSignedInAccount(requireContext())
            disableSign()
            assert(acct != null)
            updateUI(acct!!.displayName, acct.email)
            setAvatar(acct.photoUrl)
        } catch (e: ApiException) {
            Log.d("LOG", "signInResult:failed code=" + e.statusCode)
        }
    }

    private fun updateUI(nick: String?, email: String?) {
        binding.nickname.text = nick
        binding.email.text = email
    }

    private fun setAvatar(personAvatar: Uri?) {
        Glide.with(requireContext()).load(personAvatar.toString()).into(binding.imageViewAvatar)
    }

    private fun enableSign() {
        buttonSignIn.isEnabled = true
        butContinue.isEnabled = false
        buttonSignOut.isEnabled = false
        buttonSignIn.animate().alpha(1f).duration = 1000
        buttonSignOut.animate().alpha(0f).duration = 1000
        butContinue.animate().alpha(0f).duration = 1000
    }

    private fun disableSign() {
        buttonSignIn.isEnabled = false
        butContinue.isEnabled = true
        buttonSignOut.isEnabled = true
        buttonSignIn.animate().alpha(0f).duration = 1000
        buttonSignOut.animate().alpha(1f).duration = 1000
        butContinue.animate().alpha(1f).duration = 1000
    }

    companion object {
        private const val RC_SIGN_IN = 1502
    }
}