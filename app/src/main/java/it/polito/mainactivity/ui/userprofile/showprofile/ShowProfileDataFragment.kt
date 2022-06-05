package it.polito.mainactivity.ui.userprofile.showprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.firestore.FirebaseFirestoreException
import it.polito.mainactivity.R
import it.polito.mainactivity.databinding.FragmentShowProfileDataBinding
import it.polito.mainactivity.viewModel.TimeslotViewModel
import it.polito.mainactivity.ui.userprofile.SkillCard
import it.polito.mainactivity.viewModel.UserProfileViewModel
import kotlinx.coroutines.*

class ShowProfileDataFragment : Fragment() {

    private val vmUser: UserProfileViewModel by activityViewModels()
    private val vmTimeslots: TimeslotViewModel by activityViewModels()
    private var _binding: FragmentShowProfileDataBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentShowProfileDataBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val timeslotId = parentFragment?.arguments?.getString("id")

        val tabLayout = binding.tlUserData
        val viewPager = binding.vpUserData
        val adapter = ShowProfileAdapter(requireActivity(), timeslotId)

        val tabTitles = listOf("info","skills","ratings")

        viewPager.adapter = adapter
        TabLayoutMediator(tabLayout,viewPager) {
            tab, position -> tab.text = tabTitles[position]
        }.attach()






        //val balanceTextView: TextView = binding.textBalance
        //val bioTextView: TextView = binding.textBio
        //val phoneTextView: TextView = binding.textPhone
        //val locationTextView: TextView = binding.textLocation
        //val emailTextView: TextView = binding.textEmail
        //val skillsLayout = binding.skillsLayout

        //val id = parentFragment?.arguments?.getString("id")



        // If show profile of other users
        //if (id != null) {
            //val publisherId = vmTimeslots.timeslots.value?.find{ t -> t.timeslotId == id }?.publisher?.get("userId") as String
            // get publisher asynchronously and update
            //MainScope().launch{
                //val publisher = vmUser.getUserById(publisherId)
                    //?: throw FirebaseFirestoreException("publisher not found", FirebaseFirestoreException.Code.NOT_FOUND)
                //balanceTextView.text = String.format(getString(R.string.user_profile_balance_placeholder), publisher.balance)
                //bioTextView.text = String.format(getString(R.string.user_profile_bio_placeholder), publisher.bio)
                //phoneTextView.text = publisher.phone
                //locationTextView.text = publisher.location
                //emailTextView.text = publisher.email
                //skillsLayout.removeAllViews()
                //publisher.apply {
                //    skills.map { s -> SkillCard(requireContext(), s, vmUser, false) }
                //        .forEach { sc: SkillCard -> skillsLayout.addView(sc) }
                //}
            //}
        //} else { // if show profile of the current publisher
            // observe viewModel changes
            //vmUser.user.observe(viewLifecycleOwner) {
            //    balanceTextView.text = String.format(getString(R.string.user_profile_balance_placeholder), it?.balance)
            //    bioTextView.text = String.format(
            //        getString(R.string.user_profile_bio_placeholder),
            //        it?.bio ?: "null"
            //    )
            //    phoneTextView.text = it?.phone ?: "null"
            //    locationTextView.text = it?.location ?: "null"
            //    emailTextView.text = it?.email ?: "null"
            //    skillsLayout.removeAllViews()
            //    it?.apply {
            //        skills.map { s -> SkillCard(requireContext(), s, vmUser, false) }
            //            .forEach { sc: SkillCard -> skillsLayout.addView(sc) }
            //    }
            //}
        //}

        return root
    }


}
class ShowProfileAdapter(fa: FragmentActivity, val timeslotId: String?) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> ShowProfileInfoFragment(timeslotId)
            1 -> ShowProfileSkillsFragment(timeslotId)
            2 -> ShowProfileRatingsFragment(timeslotId)
            else -> ShowProfileInfoFragment(timeslotId)
        }
    }
}