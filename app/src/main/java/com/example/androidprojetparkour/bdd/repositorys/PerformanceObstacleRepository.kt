import com.example.androidprojetparkour.bdd.PerformancesDAO
import com.example.androidprojetparkour.bdd.models.PerformanceObstacleBdd
import kotlinx.coroutines.flow.Flow

class PerformanceObstacleRepository(private val performanceObstacleDao: PerformancesDAO) {
    fun getByPerformanceId(performanceId: Int): Flow<List<PerformanceObstacleBdd>> {
        return performanceObstacleDao.getPerformanceObstacles(performanceId)
    }

    suspend fun insert(performanceObstacle: PerformanceObstacleBdd) {
        performanceObstacleDao.insertPerformanceObstacle(performanceObstacle)
    }
}