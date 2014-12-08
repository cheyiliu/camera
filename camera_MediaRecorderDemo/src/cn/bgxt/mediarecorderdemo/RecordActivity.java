package cn.bgxt.mediarecorderdemo;

import java.io.File;
import android.app.Activity;
import android.media.MediaRecorder;
import android.media.MediaRecorder.OnErrorListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class RecordActivity extends Activity {
	private Button btn_RecordStart, btn_RecordStop;
	private MediaRecorder mediaRecorder;
	private boolean isRecording;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_record);

		btn_RecordStart = (Button) findViewById(R.id.btn_RecordStart);
		btn_RecordStop = (Button) findViewById(R.id.btn_RecordStop);
		
		btn_RecordStop.setEnabled(false);
		
		btn_RecordStart.setOnClickListener(click);
		btn_RecordStop.setOnClickListener(click);
	}

	private View.OnClickListener click = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_RecordStart:
				start();
				break;
			case R.id.btn_RecordStop:
				stop();
				break;
			default:
				break;
			}
		}
	};

	/**
	 * ��ʼ¼��
	 */
	protected void start() {
		try {
			File file = new File("/mnt/emmc/DCIM/100MEDIA/mediarecorder.amr");
			if (file.exists()) {
				// ����ļ����ڣ�ɾ������ʾ���뱣֤�豸��ֻ��һ��¼���ļ�
				file.delete();
			}
			mediaRecorder = new MediaRecorder();
			// ������Ƶ¼��Դ
			mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
			// ����¼����Ƶ�������ʽ
			mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
			// ������Ƶ�ı����ʽ
			mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
			// ����¼����Ƶ�ļ�����ļ�·��
			mediaRecorder.setOutputFile(file.getAbsolutePath());

			mediaRecorder.setOnErrorListener(new OnErrorListener() {
				
				@Override
				public void onError(MediaRecorder mr, int what, int extra) {
					// �������ֹͣ¼��
					mediaRecorder.stop();
					mediaRecorder.release();
					mediaRecorder = null;
					isRecording=false;
					btn_RecordStart.setEnabled(true);
					btn_RecordStop.setEnabled(false);
					Toast.makeText(RecordActivity.this, "¼���������", 0).show();
				}
			});
			
			// ׼������ʼ
			mediaRecorder.prepare();
			mediaRecorder.start();
			
			isRecording=true;
			btn_RecordStart.setEnabled(false);
			btn_RecordStop.setEnabled(true);
			Toast.makeText(RecordActivity.this, "��ʼ¼��", 0).show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ¼������
	 */
	protected void stop() {
		if (isRecording) {
			// �������¼����ֹͣ���ͷ���Դ
			mediaRecorder.stop();
			mediaRecorder.release();
			mediaRecorder = null;
			isRecording=false;
			btn_RecordStart.setEnabled(true);
			btn_RecordStop.setEnabled(false);
			Toast.makeText(RecordActivity.this, "¼������", 0).show();
		}
	}

	@Override
	protected void onDestroy() {
		if (isRecording) {
			// �������¼����ֹͣ���ͷ���Դ
			mediaRecorder.stop();
			mediaRecorder.release();
			mediaRecorder = null;
		}
		super.onDestroy();
	}

}
