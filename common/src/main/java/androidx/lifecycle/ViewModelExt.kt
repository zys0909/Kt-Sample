package  androidx.lifecycle

import android.annotation.SuppressLint
import com.zys.common.net.RequestCoroutineScopeActivityImpl
import com.zys.common.net.RequestCoroutineScopeViewModelImp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

/**
 * 描述:
 *
 * author zys
 * create by 2021/1/14
 */
private const val JOB_KEY = "androidx.androidx.lifecycle.ViewModelRequestCoroutineScope.JOB_KEY"

val ViewModel.requestCoroutineScope: RequestCoroutineScopeViewModelImp
    get() {
        val scope: RequestCoroutineScopeViewModelImp? = this.getTag(JOB_KEY)
        if (scope != null) {
            return scope
        }
        return setTagIfAbsent(
            JOB_KEY,
            RequestCoroutineScopeViewModelImp(SupervisorJob() + Dispatchers.Main.immediate)
        )
    }

val Lifecycle.requestCoroutineScope: RequestCoroutineScopeActivityImpl
    @SuppressLint("RestrictedApi")
    get() {
        while (true) {
            val existing = mInternalScopeRef.get() as RequestCoroutineScopeActivityImpl?
            if (existing != null) {
                return existing
            }
            val newScope = RequestCoroutineScopeActivityImpl(
                this,
                SupervisorJob() + Dispatchers.Main.immediate
            )
            if (mInternalScopeRef.compareAndSet(null, newScope)) {
                newScope.register()
                return newScope
            }
        }
    }

fun <X, Y> LiveData<X>.map(block: (X) -> Y): LiveData<Y> = Transformations.map(this, block)
