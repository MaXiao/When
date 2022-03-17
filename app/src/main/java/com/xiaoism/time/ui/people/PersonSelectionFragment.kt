package com.xiaoism.time.ui.people

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.xiaoism.time.model.PersonWithCity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonSelectionFragment : Fragment() {
    companion object {
        const val MULTI_CHOICE = "MULTI_CHOICE"
        const val EXISTING_MEMBER = "EXISTING_MEMBER"
    }

    private val viewModel by viewModels<PersonSelectionViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // default to be multi-choice
        viewModel.multiChoice = arguments?.getBoolean(MULTI_CHOICE) ?: true
        val existingMembers: ArrayList<PersonWithCity> =
            arguments?.getParcelableArrayList(EXISTING_MEMBER) ?: ArrayList()
        val memberIds = existingMembers.map { member -> member.person.personId }

        return ComposeView(requireContext()).apply {
            setContent {
                Content(viewModel, memberIds)
            }
        }
    }

    @Composable
    private fun Content(viewModel: PersonSelectionViewModel, memberIds: List<Long>) {
        val persons by viewModel.people.observeAsState(initial = emptyList())
        val selection by viewModel.selection.observeAsState(initial = emptyList())

        Column {
            LazyColumn() {
                items(persons.filter { p -> !memberIds.contains(p.person.personId) }) { person ->
                    val selected = selection.contains(person)

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 10.dp)
                            .background(color = if (selected) Color.Magenta else Color.Transparent)
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
            if (viewModel.multiChoice) {
                OutlinedButton(onClick = { confirmSelection() }) {
                    Text("Confirm")
                }
            }
        }
    }

    private fun onItemClick(person: PersonWithCity) {
        viewModel.toggleSelection(person)
        if (!viewModel.multiChoice) {
            confirmSelection()
        }
    }

    private fun confirmSelection() {
        viewModel.selection.value?.let { selection ->
            val intent = Intent()
            intent.putParcelableArrayListExtra(
                PersonsSelectActivityContract.PERSON_LIST,
                ArrayList(selection)
            )
            activity?.setResult(Activity.RESULT_OK, intent)
            activity?.finish()
        }

    }
}