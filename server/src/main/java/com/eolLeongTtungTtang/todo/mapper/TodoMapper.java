package com.eolLeongTtungTtang.todo.mapper;

import com.eolLeongTtungTtang.todo.domain.TagDTO;
import com.eolLeongTtungTtang.todo.domain.TodoDTO;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface TodoMapper {

    // 조건에 맞는 투두 리스트를 가져오는 메서드
    @Select("<script>" +
            "SELECT *, tag.name as tag FROM todo as t " +
            "LEFT JOIN todo_tag tt ON t.todo_id = tt.todo_id " +
            "LEFT JOIN tag tag ON tt.tag_id = tag.tag_id " +
            "WHERE 1=1 " +
            "<if test='date != null'>AND due_date = #{date}</if>" +
            "<if test='completed != null'>AND is_completed = #{completed}</if>" +
            "ORDER BY priority " +
            "<if test='limit != null'>LIMIT #{limit}</if>" +
            "</script>")
    List<TodoDTO> todoAll(@Param("date") String date, @Param("limit") Integer limit, @Param("completed") Boolean completed);

    @Select("SELECT *, tag.name as tag FROM todo as t " +
            "LEFT JOIN todo_tag tt ON t.todo_id = tt.todo_id " +
            "LEFT JOIN tag tag ON tt.tag_id = tag.tag_id " +
            "WHERE due_date < #{today} AND is_completed = false")
    List<TodoDTO> todoLast(@Param("today") String today);

    @Select("SELECT *, tag.name as tag FROM todo as t " +
            "LEFT JOIN todo_tag tt ON t.todo_id = tt.todo_id " +
            "LEFT JOIN tag tag ON tt.tag_id = tag.tag_id " +
            "WHERE t.todo_id = #{id}")
    TodoDTO selectOne(@Param("id") Integer id);

    @Select("SELECT DISTINCT due_date FROM todo WHERE is_completed = false and due_date >= #{start_dt} and due_date <= #{end_dt} ORDER BY due_date")
    List<LocalDate> selectByPeriod(@Param("start_dt") String start_dt, @Param("end_dt") String end_dt);

    // 태그 ID 조회 (태그가 존재하면 ID 반환)
    @Select("SELECT tag_id FROM tag WHERE name = #{tagName}")
    Integer findTagIdByName(@Param("tagName") String tagName);

//    @Select("S")

    // 투두 테이블에 데이터 삽입
    @Insert("INSERT INTO todo (title, memo, due_date, due_time, priority, is_completed) " +
            "VALUES (#{title}, #{memo}, #{dueDate}, #{dueTime}, #{priority}, 0)")
    @Options(useGeneratedKeys = true, keyProperty = "todoId")
    void insertTodo(TodoDTO todoDTO);

    @Insert("INSERT INTO tag (name) VALUES (#{tagName})")
    @Options(useGeneratedKeys = true, keyProperty = "tagId")
    void insertTag(TagDTO tagDTO);

    // todo_tag 테이블에 삽입, todo_id가 이미 존재한다면 update 처리
    @Insert("INSERT INTO todo_tag (todo_id, tag_id) VALUES (#{todoId}, #{tagId}) ON DUPLICATE KEY UPDATE tag_id = #{tagId}")
    void insertTodoTags(@Param("todoId") Integer todoId, @Param("tagId") Integer tagId);

    @Update("UPDATE todo SET is_completed = true WHERE todo_id = #{todo_id}")
    int completeTodo(@Param("todo_id") int todo_id);

    @Update("UPDATE todo SET title = #{title}, memo = #{memo}, due_date = #{dueDate}, due_time = #{dueTime}, priority = #{priority} WHERE todo_id = #{todoId}")
    void updateTodo(TodoDTO todoDTO);

    @Update("<script>" +
            "UPDATE todo SET is_completed = false WHERE todo_id IN " +
            "<foreach item='id' collection='todoIds' open='(' separator=',' close=')'>#{id}</foreach>" +
            "</script>"
    )
    void notCompleteTodo(@Param("todoIds") List<Integer> todoIds);

    @Delete("DELETE FROM todo WHERE todo_id = #{id}")
    void deleteTodo(@Param("id") Integer id);

    @Delete("<script>" +
            "DELETE FROM todo WHERE todo_id IN " +
            "<foreach item='id' collection='todoIds' open='(' separator=',' close=')'>#{id}</foreach>" +
            "</script>")
    void deleteTodos(@Param("todoIds") List<Integer> todoIds);

    @Delete("DELETE FROM todo_tag WHERE todo_id = #{todoId}")
    void deleteTodoTag(@Param("todoId") Integer todoId);
}
