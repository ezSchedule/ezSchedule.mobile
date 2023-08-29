package com.ezschedule.admin.presenter.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ezschedule.admin.databinding.FragmentServicesBinding
import com.ezschedule.admin.presenter.adapter.ServicesAdapter
import com.ezschedule.network.domain.presentation.ServicePresentation

class ServicesFragment : Fragment() {
    private lateinit var binding: FragmentServicesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentServicesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun getDataService() = listOf(
        ServicePresentation(
            image = "https://images.pexels.com/photos/415829/pexels-photo-415829.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
            name = "Engenheira",
            userName = "Jéssica Cardoso",
            userNumber = "(11) 96453-0362"
        ),
        ServicePresentation(
            image = "https://images.pexels.com/photos/415829/pexels-photo-415829.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
            name = "Desenvolvedora",
            userName = "Jéssica Cardoso",
            userNumber = "(11) 96453-0362"
        ),
        ServicePresentation(
            image = "https://images.pexels.com/photos/415829/pexels-photo-415829.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
            name = "Médica",
            userName = "Jéssica Cardoso",
            userNumber = "(11) 96453-0362"
        ),
        ServicePresentation(
            image = "https://images.pexels.com/photos/415829/pexels-photo-415829.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
            name = "Arquiteta",
            userName = "Jéssica Cardoso",
            userNumber = "(11) 96453-0362"
        ),
        ServicePresentation(
            image = "https://images.pexels.com/photos/415829/pexels-photo-415829.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
            name = "Atriz",
            userName = "Jéssica Cardoso",
            userNumber = "(11) 96453-0362"
        ),
        ServicePresentation(
            image = "https://images.pexels.com/photos/415829/pexels-photo-415829.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
            name = "Modelo",
            userName = "Jéssica Cardoso",
            userNumber = "(11) 96453-0362"
        ),
        ServicePresentation(
            image = "https://images.pexels.com/photos/415829/pexels-photo-415829.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
            name = "Jardineira",
            userName = "Jéssica Cardoso",
            userNumber = "(11) 96453-0362"
        )
    )

    private fun setupRecyclerView() {
        binding.fragRvService.adapter = ServicesAdapter(getDataService(), requireContext())
    }

}