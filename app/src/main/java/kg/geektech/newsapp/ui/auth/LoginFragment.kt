package kg.geektech.newsapp.ui.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import kg.geektech.newsapp.databinding.FragmentLoginBinding
import java.util.concurrent.TimeUnit

class LoginFragment : Fragment() {

    private lateinit var binding : FragmentLoginBinding
    private lateinit var callbacks : PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        phoneAuthProvider()
        binding.btnContinue.setOnClickListener {
            if (binding.editPhone.text.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Поле номера пустая, пожайлуста напишите свой номер",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val phone = binding.editPhone.text.toString()
                requestSMS(phone)
            }
        }
        binding.btnConfirm.setOnClickListener {
            findNavController().navigateUp()
        }


        /*var timer = object: CountDownTimer(20000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {}
        }
        timer.start()

        binding.tvCountdown.text = timer.toString()*/
    }

    private fun requestSMS(phone: String) {
    val phoneNumber = binding.editPhone.text.toString()
    val options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
        .setPhoneNumber(phoneNumber)         // Phone number to verify
        .setTimeout(60L, TimeUnit.SECONDS)  // Timeout and unit
        .setActivity(requireActivity())                 // Activity (for callback binding)
        .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
        .build()
    PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun phoneAuthProvider() {
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                signInByCredential(credential)
                //p0.smsCode - Код из СМСки
                Log.e("Login", "onVerificationCompleted")
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                Log.e("Login", "onVerificationFailed: ${p0.message}")
            }

            override fun onCodeSent(
                s: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                super.onCodeSent(s, token)
                binding.btnContinue.setOnClickListener {
                    verifyCode(s)
                }
            }

            private fun verifyCode(code: String) {
                val credential =
                    PhoneAuthProvider.getCredential(code, binding.editCode.text.toString())
                signInByCredential(credential)
            }

            private fun signInByCredential(credential: PhoneAuthCredential) {
                mAuth.signInWithCredential(credential)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(
                                requireContext(),
                                "Ошибка авторизации ${it.exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }
            }

        }
    }
}