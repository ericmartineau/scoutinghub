package scoutinghub

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import org.springframework.context.MessageSource
import grails.plugin.mail.MailService

class EmailVerifyService {

    MessageSource messageSource

    MailService mailService

    RecordSavingService recordSavingService

    void generateTokenForEmailValidation(Leader leader, String messageSubject) {

        //Generate token for verifying email address
        assert leader?.email: "Must pass a valid email address"
        String toHash = System.currentTimeMillis() + ""
        def hash = generateHash(toHash).substring(0, 7)
        leader.verifyHash = hash

        Leader.withTransaction {
            Leader.withSession {
                //Session gymnastics to get around goofy issues
                Leader newSessionLeader = Leader.get(leader.id)
                newSessionLeader.verifyHash = hash
                newSessionLeader.save(failOnError: true)
            }

        }


        mailService.sendMail {
            to leader.email
            from "noreply@scoutinghub.com"
            subject messageSubject
            body(view: "/emails/confirmationEmail",
                    model: [email: leader.email, hash: hash])

        }
    }

    String generateHash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return hex(md.digest(input.getBytes("UTF-8")));
        } catch (NoSuchAlgorithmException e) {
        } catch (UnsupportedEncodingException e) {
        }
        return null;
    }

    private static String hex(byte[] array) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; ++i) {
            sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).toUpperCase().substring(1, 3));
        }
        return sb.toString();
    }


}
