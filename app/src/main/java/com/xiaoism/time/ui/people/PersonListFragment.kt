package com.xiaoism.time.ui.people

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.xiaoism.time.model.PersonWithCity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonListFragment : Fragment() {
    private val viewModel by viewModels<PeopleListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                Scaffold(content = { Content(viewModel) }, floatingActionButton = { CreateBtn() })
            }
        }
    }

    @Composable
    private fun Content(viewModel: PeopleListViewModel) {
        val persons by viewModel.people.observeAsState(initial = emptyList())

        LazyColumn() {
            items(persons) { person ->
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 10.dp)
                        .clickable { onItemClick(person) }
                ) {
                    person.city?.let {
                        Text(
                            text = "${person.person.name}, ${it.name}",
                            fontSize = 20.sp
                        )
                        Text(
                            text = it.getLocalTime(),
                            fontSize = 14.sp
                        )
                    }

                }
            }
        }
    }

    @Composable
    private fun CreateBtn() {
        FloatingActionButton(modifier = Modifier.testTag("addButton"), onClick = { addPerson() }) {
            Icon(Icons.Filled.Add, "")
        }
    }

    private fun onItemClick(person: PersonWithCity) {
        val intent = Intent(requireContext(), PersonActivity::class.java)
        intent.putExtra("person", person.person.personId)
        startActivity(intent)
    }

    private fun addPerson() {
        val intent = Intent(requireActivity(), AddPersonActivity::class.java)
        startActivity(intent)
    }
}