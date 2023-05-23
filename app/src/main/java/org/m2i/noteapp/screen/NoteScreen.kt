package org.m2i.noteapp.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.m2i.noteapp.R
import org.m2i.noteapp.components.NoteButton
import org.m2i.noteapp.components.NoteInputText
import org.m2i.noteapp.data.NoteDataSource
import org.m2i.noteapp.model.Note
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(
    notes: List<Note>,
    onAddNote: (Note) -> Unit,
    onRemoveNote: (Note) -> Unit
){

    var title by remember {
        mutableStateOf("")
    }
    var description by remember {
        mutableStateOf("")
    }

    Column(modifier = Modifier.padding(6.dp)) {
        TopAppBar(title = {
                          Text(text = stringResource(id = R.string.app_name))
        }, actions = {
            Icon(imageVector = Icons.Rounded.Notifications,
                contentDescription = "Icon")
        }, colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = colorResource(id = R.color.main_color)))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NoteInputText(
                modifier= Modifier.padding(vertical = 8.dp),
                text = title,
                label = "Title",
                onTextChange = {
                    if(it.all { char ->
                            char.isLetter() || char.isWhitespace()
                        }) title = it
                })

            NoteInputText(
                modifier= Modifier.padding(vertical = 8.dp),
                text = description,
                label = "Add a note",
                onTextChange = {
                    if(it.all { char ->
                            char.isLetter() || char.isWhitespace()
                        }) description = it
                })

            NoteButton(text = "Save", onClick = {
                if(title.isNotEmpty() && description.isNotEmpty()){
                    // save and add to the list

                }
            })
        }
        Divider(modifier = Modifier.padding(10.dp))
        LazyColumn{
            items(notes){ note ->
                NoteRow(note = note, onNoteClicked = {})
            }
        }

    }
}

@Composable
fun NoteRow(
    modifier: Modifier = Modifier,
    note: Note,
    onNoteClicked: (Note) -> Unit){

    Surface(
        modifier
            .padding(4.dp)
            .clip(RoundedCornerShape(topEnd = 33.dp, bottomEnd = 33.dp))
            .fillMaxWidth(),
        color = colorResource(id = R.color.main_color),
        tonalElevation = 6.dp,
        shadowElevation = 4.dp
    ) {
        Column(
            modifier
                .clickable { }
                .padding(horizontal = 14.dp, vertical = 6.dp),
            horizontalAlignment = Alignment.Start) {

            Text(text = note.title, style = MaterialTheme.typography.titleLarge)
            Text(text = note.description, style = MaterialTheme.typography.titleMedium)
            Text(
                text = note.entryDate.format(DateTimeFormatter.ofPattern("EEE, d MMM")),
                style = MaterialTheme.typography.titleSmall)
        }
    }

}

@Preview(showBackground = true)
@Composable
fun NoteScreenPreview() {
    //NoteRow(note = NoteDataSource().loadNotes()[0], onNoteClicked = {})
    NoteScreen(notes = NoteDataSource().loadNotes(), onAddNote = {}, onRemoveNote = {})
}