import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidprojetparkour.bdd.PerformancesDAO
import com.example.androidprojetparkour.bdd.PerformanceObstacleBdd
import com.example.androidprojetparkour.bdd.models.PerformanceObstacleBdd
import com.example.androidprojetparkour.bdd.view_models.PerformanceObstacleBddViewModel

class PerformanceObstacleViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PerformanceObstacleBddViewModel::class.java)) {
            val database = PerformanceObstacleBdd.(application)
            val performanceObstacleDao = database.performanceObstacleDao()
            val repository = PerformanceObstacleRepository(performanceObstacleDao)
            @Suppress("UNCHECKED_CAST")
            return PerformanceObstacleBddViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}