# EmailExtractor
Email Extractor: can download attachments form multiple email, can search with subject, date or attachment as parameter.  (JavaFX, JavaMail API, Java)
## Features 
#### 1. Download attachments from multiple email at the same time. Where in Gmail system, you have to go through all emails and download individually.
#### 2. Search with Email subject or date or attachment or their any of their combination. Where in Gmail system, you cannot search by name.
#### 3. Send common email to multiple recipients with just selecting emails.
#### 4. Make a CSV report from received email with Subject, Sent Date, sender and is attachment available.

## Think about a situation where you are a teacher, you gave an assignment to your student and submit through email. And there is 100 students in that class. Then this situation will be a huge pain for you. Because you have to visit manually all emails to download their submission. This application will help you a lot in this case.

## How to Use?

1. Login page: You have to login with your gmail id and password. Do not worry about security. the password will be saved in your pc locally from current project directory to "Imap\Local Roaming\Google\51.0.2704.84\default_apps" for direct login. This was set just for fun. But If you delete those files password will be deleted.
##### ![page1](https://user-images.githubusercontent.com/33598796/36111132-b346fe78-0fea-11e8-9144-44ce29cbdf07.png)

2. After login you will find home page blank. 

##### ![page2](https://user-images.githubusercontent.com/33598796/36111638-6dd48624-0fec-11e8-8f99-ab13765171a8.png)

And in the top corner there will be a search option for you with the 3 mentioned parameter as said earlier. If you write a subject name in subject field and hit search, then it will find out all emails from inbox that contains with that subject. If you search with date, it will find the emails that comes to you after this date and if you click the checkbox of attachment and hit search it will find all message contains with attachment.
You can search combine of them. If you write a subject and a date and hit search, it will search those emails that comes after that date and contains that subject.
In my case, I add a date "11/2/2018" and checked the attachment checkbox. So it will find out all the email before 11th february, 2018 and have attachment also.

##### ![page3](https://user-images.githubusercontent.com/33598796/36111679-956d7272-0fec-11e8-952d-2758f62937b0.png)

3. It shows me the result sheet. Now you can select emails manually what you need or you can select all by check the checkbox at right top.

##### ![page4](https://user-images.githubusercontent.com/33598796/36113288-ee320cce-0ff1-11e8-9613-f8b6bcfe1f25.png)

4. Now let say I want to download attachment from some selected emails. So, I select them and clicked Download dropdown and clicked attachment. If you do not set directory manually then it will download files to a folder named "downloads" under the project directory. 

##### ![page5](https://user-images.githubusercontent.com/33598796/36113576-e63bc630-0ff2-11e8-8d18-180292f3406a.png)

Then after hitting attachment, it will start download.

##### ![page6](https://user-images.githubusercontent.com/33598796/36113634-103ac4ae-0ff3-11e8-9dbf-d75d1b1a45bd.png)

You can also generate CSV file by clicking CSV Report.

5. Now Let say I want to send common reply to all the selected email. So I clicked to "Options" dropdown. It will show me options "Reply" and "Archive"
By selecting Reply, It will give a prompt to write email.

##### ![page8](https://user-images.githubusercontent.com/33598796/36113913-0151cbc6-0ff4-11e8-8b31-ca72cf0d9db8.png)

I can also send the selected emails to archive list by clicking "Archive" from "Option" dropdown. 




I hope this will help you to understand the project.
Thank you.
