package me.linkcube.taku.ui.setting;

import java.util.HashMap;
import java.util.Map;

import me.linkcube.taku.R;
import me.linkcube.taku.ui.BaseTitleActivity;
import me.linkcube.taku.view.ClearEditText;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.fb.FeedbackAgent;
import com.umeng.fb.model.Conversation;
import com.umeng.fb.model.UserInfo;

public class FeedbackActivity extends BaseTitleActivity {

	private TextView edittextMexCountTv;

	private ClearEditText feedbackContentEt;

	private ClearEditText feedbackUserEmailEt;

	private FeedbackAgent agent;

	private Conversation defaultConversation;

	private static final String KEY_UMENG_CONTACT_INFO_PLAIN_TEXT = "plain";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feedback_activity);

		agent = new FeedbackAgent(this);
		defaultConversation = agent.getDefaultConversation();

		initView();
	}

	private void initView() {
		initTitleView();
		feedbackContentEt = (ClearEditText) findViewById(R.id.feed_back_content_et);
		feedbackContentEt.addTextChangedListener(textWatcher);
		feedbackUserEmailEt = (ClearEditText) findViewById(R.id.feedback_user_email_et);
		// TODO 如果用户已经登录就展示用户邮箱
		// if(UserManager.getInstance().isAuthenticated()){
		// String
		// userName=ASmackUtils.deleteServerAddress(UserManager.getInstance().getUserInfo().getJID());
		// feedbackUserEmailEt.setText(ASmackUtils.userNameDecode(userName));
		// }
		edittextMexCountTv = (TextView) findViewById(R.id.edittext_max_count_tv);

	}

	private void initTitleView() {
		setTitleText(getResources().getString(R.string.feedback_text));
		getRightTitleBtn().setText("发送");
		setRightTitleBtn(R.drawable.user_btn_bg);
		getRightTitleBtn().setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				try {
					defaultConversation.addUserReply(feedbackContentEt
							.getText().toString());
					defaultConversation.sync(null);
					UserInfo info = agent.getUserInfo();
					if (info == null)
						info = new UserInfo();
					Map<String, String> contact = info.getContact();
					if (contact == null)
						contact = new HashMap<String, String>();
					String contact_info = feedbackUserEmailEt.getEditableText()
							.toString();
					contact.put(KEY_UMENG_CONTACT_INFO_PLAIN_TEXT, contact_info);
					info.setContact(contact);
					agent.setUserInfo(info);

				} catch (Exception e) {
					e.printStackTrace();
				}
				// TODO 提交意见
				Toast.makeText(FeedbackActivity.this,
						R.string.thanks_feed_back, Toast.LENGTH_SHORT).show();
				finish();
			}
		});
	}

	private TextWatcher textWatcher = new TextWatcher() {

		CharSequence sequence = null;
		int editStart = 0;
		int editEnd = 0;

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			sequence = s;
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {

		}

		@Override
		public void afterTextChanged(Editable s) {
			editStart = feedbackContentEt.getSelectionStart();
			editEnd = feedbackContentEt.getSelectionEnd();
			edittextMexCountTv.setText(200 - sequence.length() + "");
			if (sequence.length() > 200) {
				Toast.makeText(FeedbackActivity.this,
						R.string.character_exceed, Toast.LENGTH_SHORT).show();
				s.delete(editStart - 1, editEnd);
				int tempSelection = editStart;
				feedbackContentEt.setText(s);
				feedbackContentEt.setSelection(tempSelection);
			}

		}
	};

}
