package com.example.lenovo.myapp.ui.activity.test.systemres;

import android.Manifest;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

import com.cxb.tools.utils.FastClick;
import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.model.testbean.Contact;
import com.example.lenovo.myapp.ui.adapter.OnListClickListener;
import com.example.lenovo.myapp.ui.adapter.systemres.ContactListAdapter;
import com.example.lenovo.myapp.ui.base.BaseActivity;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import static android.provider.ContactsContract.Contacts.CONTACT_CHAT_CAPABILITY;
import static android.provider.ContactsContract.Contacts.CONTACT_LAST_UPDATED_TIMESTAMP;
import static android.provider.ContactsContract.Contacts.CONTACT_PRESENCE;
import static android.provider.ContactsContract.Contacts.CONTACT_STATUS;
import static android.provider.ContactsContract.Contacts.CONTACT_STATUS_ICON;
import static android.provider.ContactsContract.Contacts.CONTACT_STATUS_LABEL;
import static android.provider.ContactsContract.Contacts.CONTACT_STATUS_RES_PACKAGE;
import static android.provider.ContactsContract.Contacts.CONTACT_STATUS_TIMESTAMP;
import static android.provider.ContactsContract.Contacts.CUSTOM_RINGTONE;
import static android.provider.ContactsContract.Contacts.DISPLAY_NAME;
import static android.provider.ContactsContract.Contacts.DISPLAY_NAME_ALTERNATIVE;
import static android.provider.ContactsContract.Contacts.DISPLAY_NAME_SOURCE;
import static android.provider.ContactsContract.Contacts.HAS_PHONE_NUMBER;
import static android.provider.ContactsContract.Contacts.IN_DEFAULT_DIRECTORY;
import static android.provider.ContactsContract.Contacts.IN_VISIBLE_GROUP;
import static android.provider.ContactsContract.Contacts.IS_USER_PROFILE;
import static android.provider.ContactsContract.Contacts.LAST_TIME_CONTACTED;
import static android.provider.ContactsContract.Contacts.LOOKUP_KEY;
import static android.provider.ContactsContract.Contacts.NAME_RAW_CONTACT_ID;
import static android.provider.ContactsContract.Contacts.PHONETIC_NAME;
import static android.provider.ContactsContract.Contacts.PHONETIC_NAME_STYLE;
import static android.provider.ContactsContract.Contacts.PHOTO_FILE_ID;
import static android.provider.ContactsContract.Contacts.PHOTO_ID;
import static android.provider.ContactsContract.Contacts.PHOTO_THUMBNAIL_URI;
import static android.provider.ContactsContract.Contacts.PHOTO_URI;
import static android.provider.ContactsContract.Contacts.PINNED;
import static android.provider.ContactsContract.Contacts.SEND_TO_VOICEMAIL;
import static android.provider.ContactsContract.Contacts.SORT_KEY_ALTERNATIVE;
import static android.provider.ContactsContract.Contacts.SORT_KEY_PRIMARY;
import static android.provider.ContactsContract.Contacts.STARRED;
import static android.provider.ContactsContract.Contacts.TIMES_CONTACTED;
import static android.provider.ContactsContract.Contacts._ID;

/**
 * 系统联系人列表
 */

public class ContactsListActivity extends BaseActivity {

    private TextView tvBack;
    private RecyclerView rvContact;
    private List<Contact> list;
    private ContactListAdapter mAdapter;
    private TextView tvContactInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list);

        initView();
        setData();

    }

    private void initView() {
        tvBack = (TextView) findViewById(R.id.tv_back);
        rvContact = (RecyclerView) findViewById(R.id.rv_contact);
        tvContactInfo = (TextView) findViewById(R.id.tv_contact_info);
    }

    private void setData() {
        tvBack.setOnClickListener(click);
        tvContactInfo.setOnClickListener(click);

        list = new ArrayList<>();
        mAdapter = new ContactListAdapter(this, list);
        mAdapter.setOnListClickListener(listClick);

        rvContact.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvContact.setAdapter(mAdapter);

        String appName = getString(R.string.app_name);
        permissions = new String[]{
                Manifest.permission.READ_CONTACTS,
        };
        refuseTips = new String[]{
                String.format(getString(R.string.text_contacts_permission_message), appName),
        };
        setPermissions();
    }

    private void showContactInfo() {
        if (!FastClick.isFastClick() && tvContactInfo.getVisibility() == View.GONE) {
            AlphaAnimation mShowAction = new AlphaAnimation(0.1f, 1f);
            mShowAction.setDuration(500);
            tvContactInfo.setAnimation(mShowAction);
            tvContactInfo.setVisibility(View.VISIBLE);
        }
    }

    private void hideContactInfo() {
        if (!FastClick.isFastClick() && tvContactInfo.getVisibility() == View.VISIBLE) {
            AlphaAnimation mHiddenAction = new AlphaAnimation(1f, 0.1f);
            mHiddenAction.setDuration(500);
            tvContactInfo.setAnimation(mHiddenAction);
            tvContactInfo.setVisibility(View.GONE);
        }
    }

    //点击监听
    private View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_back:
                    finish();
                    break;
                case R.id.tv_contact_info:
                    hideContactInfo();
                    break;
            }
        }
    };

    private OnListClickListener listClick = new OnListClickListener() {
        @Override
        public void onItemClick(int position) {
            Contact contact = list.get(position);
            String content = "";
            content += "ID：" + contact.getId() + "\n";
            content += "铃声路径：" + contact.getCustomRingtone() + "\n";
            content += "最后联系时间：" + contact.getLastTimeContacted() + "\n";
            content += "是否转到语音邮件：" + (contact.getSendToVoicemail() == 0 ? "否" : "是") + "\n";
            content += "是否收藏：" + (contact.getStarred() == 0 ? "否" : "是") + "\n";
            content += "联系次数：" + contact.getTimesContacted() + "\n";
            content += "联系人名称：" + contact.getDisplayName() + "\n";
            content += "是否有电话号码：" + (contact.getHasPhoneNumber() == 0 ? "否" : "是") + "\n";
            content += "小头像URI：" + contact.getPhotoThumbUri() + "\n";
            content += "大头像URI：" + contact.getPhotoUri() + "\n";
            content += "名字拼音首字母：" + contact.getPhonebookLabel() + "\n";

            tvContactInfo.setText(content);
            showContactInfo();

            Logger.d(content);
        }

        @Override
        public void onTagClick(int tag, int position) {

        }
    };

    @Override
    public void onPermissionSuccess() {
        ContentResolver cr = getContentResolver();
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, "phonebook_label_alt");

        if (cursor != null) {
            cursor.moveToFirst();
            do {
                String temp = "";
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    temp += cursor.getColumnName(i) + ":\t" + cursor.getString(cursor.getColumnIndex(cursor.getColumnName(i))) + "\n";
                }
                Logger.d(temp);

                Contact contact = new Contact();
                contact.setId(cursor.getLong(cursor.getColumnIndex(_ID)));

                contact.setDisplayNameAlt(cursor.getString(cursor.getColumnIndex(DISPLAY_NAME_ALTERNATIVE)));
                contact.setDisplayNameSource(cursor.getInt(cursor.getColumnIndex(DISPLAY_NAME_SOURCE)));
                contact.setPhoneticName(cursor.getString(cursor.getColumnIndex(PHONETIC_NAME)));
                contact.setPhoneticNameStyle(cursor.getInt(cursor.getColumnIndex(PHONETIC_NAME_STYLE)));
                contact.setSortKey(cursor.getString(cursor.getColumnIndex(SORT_KEY_PRIMARY)));
                contact.setSortKeyAlt(cursor.getString(cursor.getColumnIndex(SORT_KEY_ALTERNATIVE)));

                contact.setCustomRingtone(cursor.getString(cursor.getColumnIndex(CUSTOM_RINGTONE)));
                contact.setLastTimeContacted(cursor.getLong(cursor.getColumnIndex(LAST_TIME_CONTACTED)));
                contact.setSendToVoicemail(cursor.getInt(cursor.getColumnIndex(SEND_TO_VOICEMAIL)));
                contact.setStarred(cursor.getInt(cursor.getColumnIndex(STARRED)));
                contact.setTimesContacted(cursor.getInt(cursor.getColumnIndex(TIMES_CONTACTED)));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    contact.setPinned(cursor.getInt(cursor.getColumnIndex(PINNED)));
                }

                contact.setContactChatCapability(cursor.getInt(cursor.getColumnIndex(CONTACT_CHAT_CAPABILITY)));
                contact.setContactPresence(cursor.getInt(cursor.getColumnIndex(CONTACT_PRESENCE)));
                contact.setContactStatus(cursor.getString(cursor.getColumnIndex(CONTACT_STATUS)));
                contact.setContactStatusIcon(cursor.getInt(cursor.getColumnIndex(CONTACT_STATUS_ICON)));
                contact.setContactStatusLabel(cursor.getString(cursor.getColumnIndex(CONTACT_STATUS_LABEL)));
                contact.setContactStatusResPackage(cursor.getString(cursor.getColumnIndex(CONTACT_STATUS_RES_PACKAGE)));
                contact.setContactLastUpdatedTimestamp(cursor.getLong(cursor.getColumnIndex(CONTACT_LAST_UPDATED_TIMESTAMP)));

                contact.setContactStatusTs(cursor.getLong(cursor.getColumnIndex(CONTACT_STATUS_TIMESTAMP)));
                contact.setDisplayName(cursor.getString(cursor.getColumnIndex(DISPLAY_NAME)));
                contact.setHasPhoneNumber(cursor.getInt(cursor.getColumnIndex(HAS_PHONE_NUMBER)));
                contact.setInVisibleGroup(cursor.getInt(cursor.getColumnIndex(IN_VISIBLE_GROUP)));
                contact.setIsUserProfile(cursor.getInt(cursor.getColumnIndex(IS_USER_PROFILE)));
                contact.setLookup(cursor.getString(cursor.getColumnIndex(LOOKUP_KEY)));
                contact.setPhotoFileId(cursor.getLong(cursor.getColumnIndex(PHOTO_FILE_ID)));
                contact.setPhotoId(cursor.getLong(cursor.getColumnIndex(PHOTO_ID)));
                contact.setPhotoThumbUri(cursor.getString(cursor.getColumnIndex(PHOTO_THUMBNAIL_URI)));
                contact.setPhotoUri(cursor.getString(cursor.getColumnIndex(PHOTO_URI)));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    contact.setNameRawContactId(cursor.getLong(cursor.getColumnIndex(NAME_RAW_CONTACT_ID)));
                    contact.setInDefaultDirectory(cursor.getInt(cursor.getColumnIndex(IN_DEFAULT_DIRECTORY)));
                }

                contact.setPhonebookLabel(cursor.getString(cursor.getColumnIndex("phonebook_label")));
                contact.setPhonebookLabelAlt(cursor.getString(cursor.getColumnIndex("phonebook_label_alt")));
                contact.setPhonebookBucket(cursor.getInt(cursor.getColumnIndex("phonebook_bucket")));
                contact.setPhonebookBucketAlt(cursor.getString(cursor.getColumnIndex("phonebook_bucket_alt")));
                list.add(contact);
            } while (cursor.moveToNext());
            mAdapter.notifyDataSetChanged();
        }
    }
}
