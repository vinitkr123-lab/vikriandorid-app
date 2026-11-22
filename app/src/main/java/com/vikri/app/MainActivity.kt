package com.vikri.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*

private val Blue = Color(0xFF2874F0)
private data class Product(val name: String, val price: String, val emoji: String, val tint: Color)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { VikriApp() }
    }
}

@Composable
fun VikriApp() {
    val products = listOf(
        Product("Wireless Headphones", "₹1,499", "🎧", Color(0xFFE4EEFF)),
        Product("Smart Watch", "₹2,999", "⌚", Color(0xFFFFE9D7)),
        Product("Running Shoes", "₹1,899", "👟", Color(0xFFE4F5E9)),
        Product("Travel Backpack", "₹999", "🎒", Color(0xFFFFF2CC)),
        Product("Camera", "₹24,999", "📷", Color(0xFFF2E8FF)),
        Product("Coffee Maker", "₹3,499", "☕", Color(0xFFFFE5E5))
    )
    var query by remember { mutableStateOf("") }
    var cart by remember { mutableIntStateOf(0) }
    var category by remember { mutableStateOf("All") }
    val shown = products.filter { it.name.contains(query, ignoreCase = true) }

    MaterialTheme {
        Scaffold(containerColor = Color(0xFFF5F7FA), bottomBar = {
            NavigationBar(containerColor = Color.White) {
                listOf("⌂" to "Home", "▦" to "Categories", "♡" to "Wishlist", "🛒" to "Cart").forEachIndexed { i, item ->
                    NavigationBarItem(selected = i == 0, onClick = { if (i == 3) category = "Cart" }, icon = { Text(item.first, fontSize = 20.sp) }, label = { Text(if (i == 3 && cart > 0) "Cart ($cart)" else item.second) })
                }
            }
        }) { padding ->
            Column(Modifier.fillMaxSize().padding(padding).verticalScroll(rememberScrollState())) {
                Row(Modifier.fillMaxWidth().background(Blue).padding(horizontal = 18.dp, vertical = 15.dp), verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text("Vikri", color = Color.White, fontSize = 25.sp, fontWeight = FontWeight.ExtraBold)
                        Text("Delivering to your location  ▾", color = Color.White.copy(alpha = .88f), fontSize = 12.sp)
                    }
                    Text("🛒 $cart", color = Color.White, fontWeight = FontWeight.Bold)
                }
                OutlinedTextField(value = query, onValueChange = { query = it }, modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp), placeholder = { Text("Search for products") }, singleLine = true, shape = RoundedCornerShape(12.dp))
                Row(Modifier.fillMaxWidth().padding(horizontal = 16.dp).background(Color(0xFFFFE500), RoundedCornerShape(16.dp)).padding(18.dp), verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text("THE BIG SAVING DAYS", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray)
                        Text("Deals you'll love", fontSize = 23.sp, fontWeight = FontWeight.ExtraBold)
                        Text("Up to 70% off  •  Shop now →", fontSize = 13.sp)
                    }
                    Text("🛍️", fontSize = 48.sp)
                }
                Text("Shop by category", Modifier.padding(start = 16.dp, top = 22.dp, bottom = 10.dp), fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Row(Modifier.horizontalScroll(rememberScrollState()).padding(horizontal = 12.dp)) {
                    listOf("All" to "✨", "Mobiles" to "📱", "Fashion" to "👕", "Electronics" to "🎧", "Home" to "🏠", "Beauty" to "💄").forEach { (label, icon) ->
                        Column(Modifier.width(76.dp).clickable { category = label }.padding(5.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            Box(Modifier.size(54.dp).background(if (category == label) Blue.copy(alpha = .12f) else Color.White, RoundedCornerShape(18.dp)), contentAlignment = Alignment.Center) { Text(icon, fontSize = 25.sp) }
                            Text(label, fontSize = 11.sp, color = Color.DarkGray)
                        }
                    }
                }
                Row(Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 15.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text("Trending deals", Modifier.weight(1f), fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Text("View all →", color = Blue, fontSize = 13.sp)
                }
                LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.fillMaxWidth().height(520.dp).padding(horizontal = 12.dp), userScrollEnabled = false, horizontalArrangement = Arrangement.spacedBy(10.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(shown) { product ->
                        Column(Modifier.background(Color.White, RoundedCornerShape(14.dp)).clickable { cart++ }.padding(12.dp)) {
                            Box(Modifier.fillMaxWidth().height(112.dp).background(product.tint, RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center) { Text(product.emoji, fontSize = 52.sp) }
                            Text(product.name, Modifier.padding(top = 9.dp), fontSize = 13.sp, fontWeight = FontWeight.Medium)
                            Text(product.price, Modifier.padding(top = 3.dp), fontSize = 16.sp, fontWeight = FontWeight.Bold)
                            Text("★ 4.4  •  Tap to add", color = Blue, fontSize = 11.sp)
                        }
                    }
                }
            }
        }
    }
}
