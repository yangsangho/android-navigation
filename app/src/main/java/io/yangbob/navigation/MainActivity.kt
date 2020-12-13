package io.yangbob.navigation

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import io.yangbob.navigation.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var curNavController: LiveData<NavController>? = null
    private var clickBackTime: Long = 0
    private val clickWaitTime = 2000

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
        setSupportActionBar(binding.toolbar)
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
            navGraphIdList, supportFragmentManager, R.id.nav_host, intent
        ) { GlobalScope.launch { runOnUiThread { it.invoke() } } }

        controller.observe(this) {
            setupActionBarWithNavController(it)
        }

        curNavController = controller
    }

    override fun onResume() {
        Log.e("yangbob", "onResume()")
        super.onResume()
    }

    override fun onSupportNavigateUp(): Boolean {
        return curNavController?.value?.navigateUp() ?: false
    }

    @SuppressLint("RestrictedApi")
    override fun onBackPressed() {
        /* start fragment일 때 backstack count = 2 (각 Graph별 기준)
        * 1 : NavGraph (각 BottomMenu의 graph 객체)
        * 2 : Start Fragment
        */
        curNavController?.value?.backStack?.count()?.let { cntBackStack ->
            if (cntBackStack > 2) onSupportNavigateUp()
            else {
                // supportFragmentManager의 backstack count는 전체 Fragment 기준 (Home으로 모아짐)
                if (supportFragmentManager.backStackEntryCount > 0) super.onBackPressed()
                else {
                    if (System.currentTimeMillis() > clickBackTime + clickWaitTime) {
                        clickBackTime = System.currentTimeMillis()
                        Toast.makeText(this, "뒤로 가기 버튼을 한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT)
                            .show()
                        return
                    } else super.onBackPressed()
                }
            }
        } ?: super.onBackPressed()
    }
}