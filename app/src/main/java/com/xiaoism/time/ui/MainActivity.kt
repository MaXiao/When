package com.xiaoism.time.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.xiaoism.time.R
import com.xiaoism.time.ui.group.GroupListView
import com.xiaoism.time.ui.person.PersonListView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PagerContent()
        }
    }

//    override fun onBackPressed() {
//        if (viewPager.currentItem == 0) {
//            super.onBackPressed()
//        } else {
//            viewPager.currentItem = viewPager.currentItem - 1
//        }
//    }

    @Composable
    private fun PagerContent() {
        var tabIndex by remember { mutableStateOf(0) }
        val titles = listOf("Groups", "Persons")
        val pagerState = rememberPagerState()
        val scope = rememberCoroutineScope()

        Column(modifier = Modifier.testTag("Home")) {
            TabRow(selectedTabIndex = tabIndex, indicator = { tabPositions ->
                TabRowDefaults.Indicator(Modifier.pagerTabIndicatorOffset(pagerState, tabPositions))
            }) {
                titles.forEachIndexed { index, title ->
                    Tab(
                        modifier = Modifier.testTag(title),
                        selected = tabIndex == index,
                        onClick = {
                            tabIndex = index
                            scope.launch {
                                pagerState.scrollToPage(index)
                            }
                        },
                        text = { Text(title) })
                }
            }
            HorizontalPager(count = titles.size, state = pagerState) { tabIndex ->
                when (tabIndex) {
                    0 -> GroupListView()
                    1 -> PersonListView()
                    else -> Text(text = "This should not be seen")
                }

            }
        }
    }
}









































