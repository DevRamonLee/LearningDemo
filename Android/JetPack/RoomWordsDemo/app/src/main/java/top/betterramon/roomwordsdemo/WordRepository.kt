package top.betterramon.roomwordsdemo

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import org.intellij.lang.annotations.Subst

// Pass the Dao in the constructor parameter instead of DataBase, becasue you only use the dao
class WordRepository(private val wordDao: WordDao) {

    // Room executes all queries on a separate thread
    // Observer Flow will notify the observer when the data has changed
    val allWords: Flow<List<Word>> = wordDao.getAlphabetizedWords()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(word: Word) {
        wordDao.insert(word)
    }
}