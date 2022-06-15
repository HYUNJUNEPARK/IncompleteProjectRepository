package com.june.phonenumberbackup.resolver;

import android.content.ContentProviderOperation;
import android.content.Context;
import android.provider.ContactsContract;
import android.widget.Toast;

import java.util.ArrayList;

//https://gist.github.com/Antarix/d13f84c051f2f5b8d47b

public class AddContact{
  public void addContact(Context context){
    String DisplayName = "XYZ";
     String MobileNumber = "123456";
//     String HomeNumber = "1111";
//     String WorkNumber = "2222";
     String emailID = "email@nomail.com";
     String company = "bad";
     String jobTitle = "abcd";
     
     //<uses-permission android:name="android.permission.READ_CONTACTS" />
     //<uses-permission android:name="android.permission.WRITE_CONTACTS" />

           ArrayList <ContentProviderOperation> ops = new ArrayList< ContentProviderOperation >();
          
           ops.add(ContentProviderOperation.newInsert(
           ContactsContract.RawContacts.CONTENT_URI)
               .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
               .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
               .build());
          
           //------------------------------------------------------ Names
           if (DisplayName != null) {
               ops.add(ContentProviderOperation.newInsert(
               ContactsContract.Data.CONTENT_URI)
                   .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                   .withValue(ContactsContract.Data.MIMETYPE,
               ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                   .withValue(
               ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
               DisplayName).build());
           }
          
           //------------------------------------------------------ Mobile Number                     
           if (MobileNumber != null) {
               ops.add(ContentProviderOperation.
               newInsert(ContactsContract.Data.CONTENT_URI)
                   .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                   .withValue(ContactsContract.Data.MIMETYPE,
               ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                   .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, MobileNumber)
                   .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
               ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                   .build());
           }
          
           //------------------------------------------------------ Home Numbers
//           if (HomeNumber != null) {
//               ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
//                   .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
//                   .withValue(ContactsContract.Data.MIMETYPE,
//               ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
//                   .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, HomeNumber)
//                   .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
//               ContactsContract.CommonDataKinds.Phone.TYPE_HOME)
//                   .build());
//           }
          
           //------------------------------------------------------ Work Numbers
//           if (WorkNumber != null) {
//               ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
//                   .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
//                   .withValue(ContactsContract.Data.MIMETYPE,
//               ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
//                   .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, WorkNumber)
//                   .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
//               ContactsContract.CommonDataKinds.Phone.TYPE_WORK)
//                   .build());
//           }
          
           //------------------------------------------------------ Email
           if (emailID != null) {
               ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                   .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                   .withValue(ContactsContract.Data.MIMETYPE,
               ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                   .withValue(ContactsContract.CommonDataKinds.Email.DATA, emailID)
                   .withValue(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
                   .build());
           }
          
           //------------------------------------------------------ Organization
           if (!company.equals("") && !jobTitle.equals("")) {
               ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                   .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                   .withValue(ContactsContract.Data.MIMETYPE,
               ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE)
                   .withValue(ContactsContract.CommonDataKinds.Organization.COMPANY, company)
                   .withValue(ContactsContract.CommonDataKinds.Organization.TYPE, ContactsContract.CommonDataKinds.Organization.TYPE_WORK)
                   .withValue(ContactsContract.CommonDataKinds.Organization.TITLE, jobTitle)
                   .withValue(ContactsContract.CommonDataKinds.Organization.TYPE, ContactsContract.CommonDataKinds.Organization.TYPE_WORK)
                   .build());
           }
          
           // Asking the Contact provider to create a new contact                 
           try {

               context.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
           } catch (Exception e) {
               e.printStackTrace();
               Toast.makeText(context, "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
           } 
            
  }

}

