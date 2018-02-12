/*
This class will download the files from selected messages to the specific directory.

*/
package mailextractror;

import java.io.File;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.MimeBodyPart;

public class SelectedActionWork implements Runnable {

    private String Item1 = "Attachments";
    private String Item2 = "CSV Reports";
    private String path;
    private Message[] messages;
    private String choice;
    private int[] ans;
    public static int downing = 0;
    private File dir = new File("");

    public SelectedActionWork(File dir, Message[] mg, int b[], String ch) {
        int p = mg.length;
        int pp = b.length;
        this.messages = new Message[p];
        this.ans = new int[pp];
        this.dir = dir;
        this.path = this.dir.toString();
        this.messages = mg;
        this.ans = b;
        this.choice = ch;
        System.out.println("action work constructor");

    }

    @Override
    public void run() {
        if (choice == Item1) {
            try {

                System.out.println("array transferred ");
                int i, n;

                for (i = 1; i < ans.length; i++) {

                    System.out.println("array chole ");
                    System.out.println(ans.length);
                    n = messages.length;

                    if (ans[i] == 1) {
                        System.out.println("Download workinig On : " + i);
                        Message message = messages[n - i];
                        System.out.println(message.getSubject());
                        String contentType = message.getContentType();
                        String messageContent = "";
                        String attachFiles = "";
                        if (contentType.contains("multipart")) {
                            // content may contain attachments
                            Multipart multiPart = (Multipart) message.getContent();
                            int numberOfParts = multiPart.getCount();
                            for (int partCount = 0; partCount < numberOfParts; partCount++) {
                                MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(partCount);
                                if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                                    // this part is attachment
                                    String fileName = part.getFileName();
                                    attachFiles += fileName + ", ";
                                    //getting file to the dorectory
                                    part.saveFile(path + File.separator + fileName);
                                } else {
                                    // this part may be the message content
                                    messageContent = part.getContent().toString();
                                }
                            }

                            if (attachFiles.length() > 1) {
                                attachFiles = attachFiles.substring(0, attachFiles.length() - 2);
                            }
                        } else if (contentType.contains("text/plain")
                                || contentType.contains("text/html")) {
                            Object content = message.getContent();
                            if (content != null) {
                                messageContent = content.toString();
                            }
                        } else {
                        }
                        downing++;
                    }
                }
            } catch (Exception df) {
                System.out.println("Hampar Downloads");
            }

            System.out.println("Download is completed");
        }

    }
}
