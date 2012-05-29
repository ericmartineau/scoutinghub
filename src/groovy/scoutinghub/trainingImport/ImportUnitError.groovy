package scoutinghub.trainingImport

import org.springframework.context.MessageSourceResolvable

class ImportUnitError {
    ImportedUnitRecord record
    List<MessageSourceResolvable> messages = []

    ImportUnitError addMessage(MessageSourceResolvable error) {
        messages << error
        return this;
    }
}
