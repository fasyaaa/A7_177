package com.example.spa.ui.customwidget

//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.offset
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Home
//import androidx.compose.material.icons.filled.Person
//import androidx.compose.material.icons.filled.ShoppingCart
//import androidx.compose.material3.Icon
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Surface
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.unit.dp
//import androidx.navigation.NavController
//
//@Composable
//fun BottomWidget(navController: NavController) {
//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .background(Color.Gray),
//        contentAlignment = Alignment.BottomCenter
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp)
//                .background(
//                    color = Color.Black,
//                    shape = MaterialTheme.shapes.medium
//                ),
//            horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            // Left icons
//            Icon(
//                imageVector = Icons.Filled.Person,
//                contentDescription = "Profile",
//                tint = Color.White,
//                modifier = Modifier
//                    .size(24.dp)
//                    .padding(16.dp)
//                    .clickable { navController.navigate("profile") }
//            )
//            Icon(
//                imageVector = Icons.Filled.PersonAdd, //ganti gambar
//                contentDescription = "Add User",
//                tint = Color.White,
//                modifier = Modifier
//                    .size(24.dp)
//                    .padding(16.dp)
//                    .clickable { navController.navigate("addUser") }
//            )
//
//            // Center icon
//            Surface(
//                shape = CircleShape,
//                color = Color.White,
//                shadowElevation = 4.dp,
//                modifier = Modifier
//                    .size(56.dp)
//                    .offset(y = (-20).dp)
//                    .clickable { navController.navigate("home") }
//            ) {
//                Icon(
//                    imageVector = Icons.Filled.Home,
//                    contentDescription = "Home",
//                    tint = Color.Black,
//                    modifier = Modifier
//                        .size(32.dp)
//                        .padding(12.dp)
//                )
//            }
//
//            // Right icons
//            Icon(
//                imageVector = Icons.Filled.ShoppingCart,
//                contentDescription = "Cart",
//                tint = Color.White,
//                modifier = Modifier
//                    .size(24.dp)
//                    .padding(16.dp)
//                    .clickable { navController.navigate("cart") }
//            )
//            Icon(
//                imageVector = Icons.Filled.CalendarToday, // ganti gambar
//                contentDescription = "Calendar",
//                tint = Color.White,
//                modifier = Modifier
//                    .size(24.dp)
//                    .padding(16.dp)
//                    .clickable { navController.navigate("calendar") }
//            )
//        }
//    }
//}