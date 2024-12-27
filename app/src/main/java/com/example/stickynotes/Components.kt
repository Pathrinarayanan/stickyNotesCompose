package com.example.stickynotes

import android.os.Build
import android.provider.ContactsContract
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch

@Composable
fun ItemDetailsPage(viewModel: MainViewModel, navController: NavController){
    val notes by viewModel.getNotes().observeAsState(initial = emptyList())
    Column (modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFFF8EEE2))
        .padding(start = 16.dp, top = 16.dp)
    ){
        topBar()
        LazyVerticalStaggeredGrid(columns = StaggeredGridCells.Fixed(2), content = {
            notes.forEach {
                item {
                    SingleItem(it,
                        onDelete = {
                            viewModel.viewModelScope.launch {
                                viewModel.deleteNotes(it)
                            }
                        },
                        onEdit = {
                            navController.navigate("add/true/${it.title}/${it.subTitle}/${it.id}")
                        },
                        modifier =  Modifier
                    )

                }
            }
        }
        )
    }
}

//Narayanan


@Composable
fun SingleItem(notes: Notes, onDelete: (Int) ->Unit , onEdit : (Notes) ->Unit, modifier : Modifier ) {
    var expanded by remember { mutableStateOf(false)}

    Column(modifier = modifier
        .wrapContentHeight()
        .fillMaxWidth()
        .padding(bottom = 16.dp, end = 16.dp)
        .clip(RoundedCornerShape(16.dp))
        .background(Color.White)
        .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            notes.title,
            fontSize = 16.sp,
            fontWeight = FontWeight.W600
        )
        Text(
            notes.subTitle,
            fontSize = 14.sp,
            fontWeight = FontWeight.W400
        )
        Icon(
            Icons.Filled.MoreVert,
            contentDescription = null,
            modifier = Modifier
                .size(20.dp)
               .align(Alignment.End)
                .clickable {
                    expanded  = !expanded
                }
        )
        DropdownMenu(expanded, onDismissRequest = {
            expanded = false
        }) {

            DropdownMenuItem(
                text = {
                    Text("Edit")
                },
                onClick = {
                    ///onEdit()
                    onEdit(notes)
                }
            )
            DropdownMenuItem(
                text = {
                    Text("Delete")
                },
                onClick = {
                        onDelete(notes.id)
                    expanded = false
                }
            )
        }

    }
}

@Composable
fun topBar() {
    Row (modifier = Modifier.padding(bottom = 20.dp)){
        val context = LocalContext.current
        Icon(
            Icons.Filled.KeyboardArrowLeft,
            contentDescription = null,
            modifier = Modifier
                .size(20.dp)
                .weight(0.1f).clickable {
                    Toast.makeText(context, "Thank you", Toast.LENGTH_SHORT).show()
                    System.exit(0)
                }
        )
        Text("My Notes", modifier = Modifier.weight(0.8f), textAlign = TextAlign.Center,
            fontWeight = FontWeight.W900,
            fontFamily = FontFamily.Cursive,
            fontSize = 24.sp
        )

        Icon(
            Icons.Filled.MoreVert,
            contentDescription = null,
            modifier = Modifier
                .size(20.dp)
                .weight(0.1f)
        )
    }
}
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddorEdit(viewModel: MainViewModel, navController: NavHostController, isEdit:Boolean= false, title :String?="", message :String?="", id:Int =0){
    var title by remember { mutableStateOf(title) }
    var message by remember { mutableStateOf(message) }
    val context = LocalContext.current
    Column (modifier = Modifier.fillMaxSize().background(colorResource(R.color.body)).padding(top=16.dp)){
        Text(if(isEdit) "Edit Sticky notes !!" else "Add Sticky Notes",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.W600
        )
        TextField(
            value = title ?:"",
            
            onValueChange = {
                title = it
            },
            placeholder = {
                Text("Enter the title ", style = TextStyle(color = Color.DarkGray))
            },
            modifier = Modifier.fillMaxWidth(). padding(16.dp).border(width = 1.dp, color = Color.DarkGray, shape = RoundedCornerShape(12.dp)).background(shape = RoundedCornerShape(12.dp), color = Color.White) ,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
        TextField(
            value = message ?:"",

            onValueChange = {
                message = it
            },
            placeholder = {
                Text("Enter the Message ")
            },
            modifier = Modifier.heightIn(200.dp, 600.dp).fillMaxWidth().padding(16.dp)
                .background(shape = RoundedCornerShape(12.dp), color = Color.White )
                .border(width = 1.dp, color = Color.DarkGray, shape = RoundedCornerShape(12.dp)),
           colors = TextFieldDefaults.textFieldColors(
               containerColor = Color.Transparent,
               focusedIndicatorColor = Color.Transparent,
               unfocusedIndicatorColor = Color.Transparent
           )
        )
        Button(content = {
            Text(if(!isEdit)"Save" else "Edit and Save")
        },
            onClick = {
                viewModel.viewModelScope.launch {
                    if(title!= "" && message!= "") {
                        if(!isEdit)
                            viewModel.addNotes(title?:"", subTitle = message ?:"")
                        else
                            viewModel.updateNotes(title =title ?:"", subtitle =message ?:"" ,id =id)
                        navController.popBackStack()
                        Toast.makeText(context,"success", Toast.LENGTH_SHORT).show()

                    }
                    else{
                        Toast.makeText(context,"message or title is not empty", Toast.LENGTH_SHORT).show()
                    }
                }
               //
            },
            modifier  = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
        )
    }
}