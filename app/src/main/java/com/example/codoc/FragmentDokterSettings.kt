package com.example.codoc

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import com.example.codoc.activity.FirstActivity
import com.example.codoc.activity.dokter.ProfileDokterActivity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentDokterHome.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentDokterSettings : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dokter_settings, container, false)

        // Find the LinearLayout using the inflated view
        val aboutUs: LinearLayout = view.findViewById(R.id.linearLayout2)
        aboutUs.setOnClickListener {
            showAboutUsDialog()
        }

        // Find the LinearLayout using the inflated view
        val feedback: LinearLayout = view.findViewById(R.id.linearLayout4)
        feedback.setOnClickListener {
            sendFeedbackEmail()
        }

        // Find the LinearLayout using the inflated view
        val editProfile: LinearLayout = view.findViewById(R.id.linearLayout1)
        editProfile.setOnClickListener {
            val intentSettingActivity = Intent(requireContext(), ProfileDokterActivity::class.java)
            startActivity(intentSettingActivity)
        }

        // Find the LinearLayout using the inflated view
        val logOut: LinearLayout = view.findViewById(R.id.linearLayout5)
        logOut.setOnClickListener {
            val intentSettingActivity = Intent(requireContext(), FirstActivity::class.java)
            startActivity(intentSettingActivity)
        }
        return view
    }

    private fun showAboutUsDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("About Us")
            .setMessage("Kami adalah tim pengembang yang berkomitmen untuk menyediakan pengalaman pengguna terbaik dalam pendaftaran janji dokter. " +
                    "Aplikasi CRUD (Create, Read, Update, Delete) kami diciptakan dengan tujuan untuk memberikan solusi yang ramah pengguna, cepat, dan dapat diandalkan bagi pengguna yang ingin membuat janji dengan dokter mereka.")
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .setNeutralButton("Learn More") { _, _ ->
                // Create an implicit intent to open the URL
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/samlehoy/Codoc-Project-Appointment-App"))

                // Check if there's an activity that can handle the intent
                if (intent.resolveActivity(requireActivity().packageManager) != null) {
                    startActivity(intent)
                } else {
                    // Handle the case where no app is available to handle the intent
                    // You can display a Toast or show an AlertDialog
                    // indicating that no app is available.
                }
            }

        val dialog = builder.create()
        dialog.show()
    }

    private fun sendFeedbackEmail() {
        // Create an implicit intent to send an email
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:muttaqien0111@gmail.com") // Set the email address

        intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback") // Set the email subject

        // You can customize the email body if needed
        intent.putExtra(
            Intent.EXTRA_TEXT,
            "Dear Team,\n\nI would like to provide the following feedback:\n\n"
        )

        // Check if there's an activity that can handle the email intent
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(intent)
        } else {
            // Handle the case where no email app is available
            // You can display a Toast or show an AlertDialog
            // indicating that no email app is installed.
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentDokterSettings().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
