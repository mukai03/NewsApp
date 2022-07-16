package kg.geektech.newsapp.room

import androidx.room.*
import kg.geektech.newsapp.models.News

@Dao
interface NewsDao {

    @Query("SELECT * FROM news")
    fun getAll() : List<News>

    @Insert
    fun insert(news: News)

    @Update
    fun update (news: News)

    @Delete
    fun deleteItem(news: News)

    @Query("SELECT * FROM news ORDER BY createdAt Desc")
    fun sortAll() : List<News>

    @Query("SELECT * FROM news WHERE title LIKE '%' || :search || '%'")
    fun getSearch(search : String?) : List<News>

    @Query("SELECT * FROM news ORDER BY title ASC")
    fun sort(): List<News>

}
