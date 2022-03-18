
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.demo.newsapplication.base.BaseActivity
import com.demo.webrtc.R
import com.demo.webrtc.databinding.ActvitiyInformationBinding
import com.demo.webrtc.databinding.ActvitiyLoginBinding
import com.demo.webrtc.models.UserLoginRequest
import com.demo.webrtc.utils.gone
import com.demo.webrtc.utils.isEmailValid
import com.demo.webrtc.utils.visible
import kotlinx.android.synthetic.main.actvitiy_login.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import timber.log.Timber
import java.util.*


@DelicateCoroutinesApi
@ObsoleteCoroutinesApi
class InformationActivity : BaseActivity<ActvitiyInformationBinding>(R.layout.actvitiy_information),
    View.OnClickListener {
    private val viewModel: LoginActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        clickListener()
//        setUpObserver()
    }


    private fun clickListener() {
    }

    override fun onClick(v: View) {
        when (v.id) {

        }

    }


}

