package scoutcert.trainingImport

import org.springframework.context.MessageSourceResolvable

/**
 * User: eric
 * Date: 3/26/11
 * Time: 10:07 PM
 */
class ImportError {
    ImportedRecord record
    List<MessageSourceResolvable> messages = []

    ImportError addMessage(MessageSourceResolvable error) {
        messages << error
        return this;
    }
}
