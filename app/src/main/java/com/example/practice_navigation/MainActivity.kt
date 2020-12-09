package com.example.practice_navigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.practice_navigation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var curNavController: LiveData<NavController>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        if (savedInstanceState == null) {
            setSupportActionBar(binding.toolbar)
            setUpBottomNavigationBar()
        }
    }

    // 화면이 회전되게 되면 해당 Activity는 onCreate부터 다시 생명주기 함수들을 호출합니다.
    // 이때 savedInstanceState은 null이 아니게 되며, onRestoreInstanceState이 호출되게 됩니다.
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setUpBottomNavigationBar()
    }

    private fun setUpBottomNavigationBar() {
        val navGraphIdList = listOf(
                R.navigation.nav_home,
                R.navigation.nav_dose,
                R.navigation.nav_prescription,
                R.navigation.nav_health
        )
        val controller = binding.bottom.setupWithNavController(
                navGraphIdList, supportFragmentManager, R.id.nhf, intent
        )
        controller.observe(this) {
            setupActionBarWithNavController(it)
        }
        curNavController = controller
    }

    override fun onSupportNavigateUp(): Boolean {
        return curNavController?.value?.navigateUp() ?: false
    }
}