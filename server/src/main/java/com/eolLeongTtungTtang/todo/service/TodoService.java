package com.eolLeongTtungTtang.todo.service;

import com.eolLeongTtungTtang.todo.domain.TagDTO;
import com.eolLeongTtungTtang.todo.mapper.TodoMapper;
import com.eolLeongTtungTtang.todo.domain.TodoDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TodoService {

    private final TodoMapper todoMapper;

    public TodoService(TodoMapper todoMapper) {
        this.todoMapper = todoMapper;
    }

    public List<TodoDTO> getByDate(String date, Integer limit) throws ParseException {

        if(date != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            formatter.parse(date);
        }

        return todoMapper.todoAll(date, limit, false);
    }

    public Map<String, List<TodoDTO>> getLastAndToday(){

        String todayDate = LocalDate.now().toString();

        List<TodoDTO> today = todoMapper.todoAll(todayDate, null, false);
        List<TodoDTO> last = todoMapper.todoLast(todayDate);

        Map<String, List<TodoDTO>> map = new HashMap<>();
        map.put("today", today);
        map.put("last", last);
        return map;
    }

    public TodoDTO getOneTodo(int todo_id) {
        return todoMapper.selectOne(todo_id);
    }

    public List<LocalDate> getByPeriod(String start_dt, String end_dt) throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        formatter.parse(start_dt);
        formatter.parse(end_dt);

        return todoMapper.selectByPeriod(start_dt, end_dt);
    }

    public List<TodoDTO> getCompleted() {
        return todoMapper.todoAll(null, null, true);
    }

    public List<TodoDTO> getAll() {
        return todoMapper.todoAll(null, null, null);
    }


    @Transactional
    public TodoDTO insertTodo(TodoDTO todoDTO) {
        // 1. todo 삽입
        todoMapper.insertTodo(todoDTO);

        Optional<String> tagOpt = todoDTO.getTag();

        if (tagOpt.isPresent()){
            String tagName = tagOpt.get();

            Integer tagId = todoMapper.findTagIdByName(tagName);

            if (tagId == null) {
                // 태그가 없으면 새 태그 삽입 후 ID 가져오기
                TagDTO newTag = new TagDTO();
                newTag.setTagName(tagName);
                todoMapper.insertTag(newTag);
                tagId = newTag.getTagId();
            }

            // todo_tag 테이블에 (todo_id, tag_id) 삽입
            if (tagId != null) {
                todoMapper.insertTodoTags(todoDTO.getTodoId(), tagId);
            }

        }

        return todoDTO;
    }

    public int completeTodo(int todo_id){
        return todoMapper.completeTodo(todo_id);
    }

    @Transactional
    public TodoDTO updateTodo(TodoDTO todoDTO, int todo_id) {

        todoDTO.setTodoId((todo_id));
        todoMapper.updateTodo(todoDTO);

        Optional<String> tagOpt = todoDTO.getTag();

        if (tagOpt.isPresent()){

            String  tagName = tagOpt.get();
            Integer tagId = todoMapper.findTagIdByName(tagName);

            if (tagId == null) {
                // 태그가 없으면 새 태그 삽입 후 ID 가져오기
                TagDTO newTag = new TagDTO();
                newTag.setTagName(tagName);
                todoMapper.insertTag(newTag);
                tagId = newTag.getTagId();
            }

            // todo_tag 테이블에 (todo_id, tag_id) 삽입
            if (tagId != null) {
                todoMapper.insertTodoTags(todoDTO.getTodoId(), tagId);
            }

        }else {
            // 태그가 없을 경우, 기존에 태그가 없다면 삭제해야 함.
            todoMapper.deleteTodoTag(todoDTO.getTodoId());

        }

        return todoDTO;
    }

    public void notCompleteTodo(List<Integer> todo_ids) {
        todoMapper.notCompleteTodo(todo_ids);
    }

    public void deleteTodo(int todo_id) {
        todoMapper.deleteTodo(todo_id);
    }

    public void deleteTodos(List<Integer> todo_ids) {
        todoMapper.deleteTodos(todo_ids);
    }
}
