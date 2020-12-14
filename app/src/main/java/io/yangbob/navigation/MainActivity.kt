package io.yangbob.navigation

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.ui.setupActionBarWithNavController
import io.yangbob.navigation.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var curNavController: LiveData<NavController>? = null
    private var clickBackTime: Long = 0
    private val clickWaitTime = 2000

    // todo fragment 재활용 테스트 - include(nested graph?)같은... 한곳에서 변경하면 다 같이 수정될 수 있도록? (관리 편하게 하기 위해)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        if (savedInstanceState == null) {
            initView()
        }
    }

    // 화면이 회전되게 되면 해당 Activity는 onCreate부터 다시 생명주기 함수들을 호출합니다.
    // 이때 savedInstanceState은 null이 아니게 되며, onRestoreInstanceState이 호출되게 됩니다.
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        initView()
    }

    private fun initView() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
        }
        setUpBottomNavigationBar()
    }

    private fun setUpBottomNavigationBar() {
        val navGraphIdList = listOf(
            R.navigation.nav_home,
            R.navigation.nav_dose,
            R.navigation.nav_prescription,
            R.navigation.nav_health
        )

        // NavHost(Graph로부터 만드는) -> NavController -> Destination(Fragment)
        // 기존에는 NavHost의 xml에 graph를 지정해서 fragmentManager로 별 작업 없이 됐지만
        // BottomNavigationView에서는 각각 탭마다 NavHost를 각각 생성해서 저장해서 사용
        val controller = binding.bottom.setupWithNavController(
            navGraphIds = navGraphIdList,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host,
            intent = intent,
            setAppbarTitle = { title -> binding.tvTitle.text = title },
            setPopAction = { popAction -> GlobalScope.launch { runOnUiThread { popAction.invoke() } } }
            // setPopAction으로 탭 이동시 firstDestination으로 움직이는 것보다
            // 애초에 화면 구성을 화면 전환 시 탭이 보이지 않도록 하는 방식으로 구성하는 것이 좋을 것 같다.
            // todo 화면 이동시 BottomNavigationView show & hide 자연스럽게 되나? (공식 가이드 있었음)
        )

        controller.observe(this) {
            // NavHost(NavController) 바뀔 때마다, 새로 appbar와 연동
            binding.tvTitle.text = it.currentDestination?.label
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