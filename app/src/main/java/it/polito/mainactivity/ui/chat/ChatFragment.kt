package it.polito.mainactivity.ui.chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.google.firebase.Timestamp
import it.polito.mainactivity.R
import it.polito.mainactivity.databinding.FragmentChatListBinding
import it.polito.mainactivity.databinding.FragmentRequestsListBinding
import it.polito.mainactivity.model.*
import it.polito.mainactivity.placeholder.PlaceholderContent
import it.polito.mainactivity.ui.home.FilteredTimeslotListFragmentArgs
import it.polito.mainactivity.viewModel.TimeslotViewModel
import java.util.*

class ChatFragment : Fragment() {
    private val vm: TimeslotViewModel by activityViewModels()
    private var _binding: FragmentChatListBinding? = null
    private val binding get() = _binding!!
    var adapter: MessageRecyclerViewAdapter? = null

    var ts: Timeslot? = null
    var chat: Chat? = null

    private val args: ChatFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChatListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val rv: RecyclerView = binding.list
        rv.layoutManager = LinearLayoutManager(root.context)


        vm.timeslots.observe(viewLifecycleOwner){
            ts =vm.timeslots.value?.find { it.timeslotId == args.timeslotId }
            chat = ts?.chats?.find { it.chatId == args.chatId }
            adapter = MessageRecyclerViewAdapter(
                chat!!.messages, chat!!, ts!!, this
            )
            rv.adapter = adapter
            rv.scrollToPosition(chat!!.messages.size - 1);

            when (ts!!.status) {
                Timeslot.Status.PUBLISHED -> {
                    binding.btnsManageReq.visibility = View.VISIBLE
                    binding.ratingZone.visibility = View.GONE
                }
                Timeslot.Status.COMPLETED -> {
                    binding.ratingZone.visibility = View.VISIBLE
                    binding.textMsg.visibility = View.GONE
                    binding.btnSendMsg.visibility = View.GONE
                    binding.btnsManageReq.visibility = View.GONE
                }
                Timeslot.Status.ASSIGNED -> {
                    binding.btnsManageReq.visibility = View.GONE
                    binding.ratingZone.visibility = View.GONE
                }
                else -> {}
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}