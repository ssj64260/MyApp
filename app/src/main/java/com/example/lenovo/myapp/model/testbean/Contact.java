package com.example.lenovo.myapp.model.testbean;

import java.io.Serializable;

/**
 * 联系人
 * {@link android.provider.BaseColumns}
 * {@link android.provider.ContactsContract.ContactNameColumns}
 * {@link android.provider.ContactsContract.ContactOptionsColumns}
 * {@link android.provider.ContactsContract.ContactStatusColumns}
 * {@link android.provider.ContactsContract.ContactsColumns}
 */

public class Contact implements Serializable {

    //BaseColumns
    private long id;    //ID    （_id）

    //ContactNameColumns
    private String displayNameAlt;  //显示联系人名称          （display_name_alt）
    private int displayNameSource;  //联系人名称类型          （display_name_source）
    private String phoneticName;    //联系人名称拼音          （phonetic_name）
    private int phoneticNameStyle;  //联系人名称拼音类型       （phonetic_name_style）
    private String sortKeyAlt;      //排序用名称拼音          （sort_key_alt）
    private String sortKey;         //排序关键字（姓名拼音）   （sort_key）

    //ContactOptionsColumns
    private String customRingtone;  //铃声路径         （custom_ringtone）
    private long lastTimeContacted; //最后联系时间      （last_time_contacted）
    private int sendToVoicemail;    //是否转到语音邮件  （send_to_voicemail）
    private int starred;            //是否加星标记      （starred）
    private int timesContacted;     //联系次数         （times_contacted）
    private int pinned;             //                 （pinned）

    //ContactStatusColumns
    private int contactChatCapability;      //联系人聊天功能          （contact_chat_capability）
    private int contactPresence;            //联系人状态             （contact_presence）
    private String contactStatus;           //联系人的最新状态更新    （contact_status）
    private int contactStatusIcon;          //联系人状态图标          （contact_status_icon）
    private String contactStatusLabel;      //联系人状态标签          （contact_status_label）
    private String contactStatusResPackage; //联系人状态资源包        （contact_status_res_package）
    private long contactStatusTs;           //联系人状态的绝对时间    （contact_status_ts）

    //ContactsColumns
    private long contactLastUpdatedTimestamp;   //最后更新时间戳       （contact_last_updated_timestamp）
    private String displayName;                 //显示联系人名称       （display_name）
    private int hasPhoneNumber;                 //是否有电话号码       （has_phone_number）
    private int inVisibleGroup;                 //是否在可见组         （in_visible_group）
    private int isUserProfile;                  //是否用户配置         （is_user_profile）
    private String lookup;                      //                    （lookup）
    private long photoFileId;                   //大头像ID             （photo_file_id）
    private long photoId;                       //小头像ID，数据库行    （photo_id）
    private String photoThumbUri;               //小头像URI            （photo_thumb_uri）
    private String photoUri;                    //大头像URI            （photo_uri）
    private long nameRawContactId;              //该联系人表中所在行    （name_raw_contact_id）
    private int inDefaultDirectory;             //是否在默认目录        （in_default_directory）

    //
    private String phonebookLabelAlt;   //名字拼音首字母   （phonebook_label_alt）
    private int phonebookBucket;        //首字母所在位置   （phonebook_bucket）
    private String phonebookLabel;      //名字拼音首字母   （phonebook_label）
    private String phonebookBucketAlt;  //首字母所在位置   （phonebook_bucket_alt）

    public long getLastTimeContacted() {
        return lastTimeContacted;
    }

    public void setLastTimeContacted(long lastTimeContacted) {
        this.lastTimeContacted = lastTimeContacted;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDisplayNameAlt() {
        return displayNameAlt;
    }

    public void setDisplayNameAlt(String displayNameAlt) {
        this.displayNameAlt = displayNameAlt;
    }

    public int getDisplayNameSource() {
        return displayNameSource;
    }

    public void setDisplayNameSource(int displayNameSource) {
        this.displayNameSource = displayNameSource;
    }

    public String getPhoneticName() {
        return phoneticName;
    }

    public void setPhoneticName(String phoneticName) {
        this.phoneticName = phoneticName;
    }

    public int getPhoneticNameStyle() {
        return phoneticNameStyle;
    }

    public void setPhoneticNameStyle(int phoneticNameStyle) {
        this.phoneticNameStyle = phoneticNameStyle;
    }

    public String getSortKeyAlt() {
        return sortKeyAlt;
    }

    public void setSortKeyAlt(String sortKeyAlt) {
        this.sortKeyAlt = sortKeyAlt;
    }

    public String getSortKey() {
        return sortKey;
    }

    public void setSortKey(String sortKey) {
        this.sortKey = sortKey;
    }

    public String getCustomRingtone() {
        return customRingtone;
    }

    public void setCustomRingtone(String customRingtone) {
        this.customRingtone = customRingtone;
    }

    public int getSendToVoicemail() {
        return sendToVoicemail;
    }

    public void setSendToVoicemail(int sendToVoicemail) {
        this.sendToVoicemail = sendToVoicemail;
    }

    public int getStarred() {
        return starred;
    }

    public void setStarred(int starred) {
        this.starred = starred;
    }

    public int getTimesContacted() {
        return timesContacted;
    }

    public void setTimesContacted(int timesContacted) {
        this.timesContacted = timesContacted;
    }

    public int getPinned() {
        return pinned;
    }

    public void setPinned(int pinned) {
        this.pinned = pinned;
    }

    public int getContactChatCapability() {
        return contactChatCapability;
    }

    public void setContactChatCapability(int contactChatCapability) {
        this.contactChatCapability = contactChatCapability;
    }

    public int getContactPresence() {
        return contactPresence;
    }

    public void setContactPresence(int contactPresence) {
        this.contactPresence = contactPresence;
    }

    public String getContactStatus() {
        return contactStatus;
    }

    public void setContactStatus(String contactStatus) {
        this.contactStatus = contactStatus;
    }

    public int getContactStatusIcon() {
        return contactStatusIcon;
    }

    public void setContactStatusIcon(int contactStatusIcon) {
        this.contactStatusIcon = contactStatusIcon;
    }

    public String getContactStatusLabel() {
        return contactStatusLabel;
    }

    public void setContactStatusLabel(String contactStatusLabel) {
        this.contactStatusLabel = contactStatusLabel;
    }

    public String getContactStatusResPackage() {
        return contactStatusResPackage;
    }

    public void setContactStatusResPackage(String contactStatusResPackage) {
        this.contactStatusResPackage = contactStatusResPackage;
    }

    public long getContactStatusTs() {
        return contactStatusTs;
    }

    public void setContactStatusTs(long contactStatusTs) {
        this.contactStatusTs = contactStatusTs;
    }

    public long getContactLastUpdatedTimestamp() {
        return contactLastUpdatedTimestamp;
    }

    public void setContactLastUpdatedTimestamp(long contactLastUpdatedTimestamp) {
        this.contactLastUpdatedTimestamp = contactLastUpdatedTimestamp;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public int getHasPhoneNumber() {
        return hasPhoneNumber;
    }

    public void setHasPhoneNumber(int hasPhoneNumber) {
        this.hasPhoneNumber = hasPhoneNumber;
    }

    public int getInVisibleGroup() {
        return inVisibleGroup;
    }

    public void setInVisibleGroup(int inVisibleGroup) {
        this.inVisibleGroup = inVisibleGroup;
    }

    public int getIsUserProfile() {
        return isUserProfile;
    }

    public void setIsUserProfile(int isUserProfile) {
        this.isUserProfile = isUserProfile;
    }

    public String getLookup() {
        return lookup;
    }

    public void setLookup(String lookup) {
        this.lookup = lookup;
    }

    public long getPhotoFileId() {
        return photoFileId;
    }

    public void setPhotoFileId(long photoFileId) {
        this.photoFileId = photoFileId;
    }

    public long getPhotoId() {
        return photoId;
    }

    public void setPhotoId(long photoId) {
        this.photoId = photoId;
    }

    public String getPhotoThumbUri() {
        return photoThumbUri;
    }

    public void setPhotoThumbUri(String photoThumbUri) {
        this.photoThumbUri = photoThumbUri;
    }

    public String getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }

    public long getNameRawContactId() {
        return nameRawContactId;
    }

    public void setNameRawContactId(long nameRawContactId) {
        this.nameRawContactId = nameRawContactId;
    }

    public int getInDefaultDirectory() {
        return inDefaultDirectory;
    }

    public void setInDefaultDirectory(int inDefaultDirectory) {
        this.inDefaultDirectory = inDefaultDirectory;
    }

    public String getPhonebookLabelAlt() {
        return phonebookLabelAlt;
    }

    public void setPhonebookLabelAlt(String phonebookLabelAlt) {
        this.phonebookLabelAlt = phonebookLabelAlt;
    }

    public int getPhonebookBucket() {
        return phonebookBucket;
    }

    public void setPhonebookBucket(int phonebookBucket) {
        this.phonebookBucket = phonebookBucket;
    }

    public String getPhonebookLabel() {
        return phonebookLabel;
    }

    public void setPhonebookLabel(String phonebookLabel) {
        this.phonebookLabel = phonebookLabel;
    }

    public String getPhonebookBucketAlt() {
        return phonebookBucketAlt;
    }

    public void setPhonebookBucketAlt(String phonebookBucketAlt) {
        this.phonebookBucketAlt = phonebookBucketAlt;
    }
}
