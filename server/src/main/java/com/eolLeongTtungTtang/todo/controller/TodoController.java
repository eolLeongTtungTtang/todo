package com.eolLeongTtungTtang.todo.controller;

import com.eolLeongTtungTtang.todo.domain.ApiResponse;
import com.eolLeongTtungTtang.todo.domain.TodoDTO;
import com.eolLeongTtungTtang.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/todos")
public class TodoController {

    private final TodoService todoService;



    @GetMapping("")
    public ResponseEntity<ApiResponse> findTodoByDate(@RequestParam(required = false) String date, @RequestParam(required = false) Integer limit) {
        try {

                List<TodoDTO> todos = todoService.getByDate(date, limit);
                // 정상적인 데이터 조회
                return ResponseEntity.ok(ApiResponse.builder()
                        .success(true)
                        .data(todos)
                        .build());

        } catch (ParseException parseException) {
            // 날짜 형식이 잘못 되었을 경우 예외 처리
            return ResponseEntity.badRequest().body(ApiResponse.builder()
                            .success(false)
                            .message("잘못된 날짜 포맷입니다. 올바른 날짜 형식: yyyy-MM-dd")
                            .build());
        } catch (Exception e) {
            // 예기치 않은 예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.builder()
                    .success(false)
                    .message("요청 처리 중 오류가 발생했습니다.")
                    .build());
        }
    }


    @GetMapping("/last")
    public ResponseEntity<ApiResponse> findTodoAll(){
        try {
            Map<String, List<TodoDTO>> todos = todoService.getLastAndToday();

            // 정상적인 데이터 조회
            return ResponseEntity.ok(ApiResponse.builder()
                    .success(true)
                    .data(todos)
                    .build());

        } catch (Exception e) {
            // 예기치 않은 예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.builder()
                    .success(false)
                    .message("요청 처리 중 오류가 발생했습니다.")
                    .build());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> findTodoById(@PathVariable int id) {
        try {
            TodoDTO todos = todoService.getOneTodo(id);

            // 정상적인 데이터 조회
            return ResponseEntity.ok(ApiResponse.builder()
                    .success(true)
                    .data(todos)
                    .build());

        } catch (Exception e) {
            // 예기치 않은 예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.builder()
                    .success(false)
                    .message("요청 처리 중 오류가 발생했습니다.")
                    .build());
        }

    }

    @GetMapping("/cal")
    public ResponseEntity<ApiResponse> findTodoByPeriod(@RequestParam String start_dt, @RequestParam String end_dt) {
        try {

            List<LocalDate> todos = todoService.getByPeriod(start_dt, end_dt);
            // 정상적인 데이터 조회
            return ResponseEntity.ok(ApiResponse.builder()
                    .success(true)
                    .data(todos)
                    .build());

        } catch (ParseException parseException) {
            // 날짜 형식이 잘못 되었을 경우 예외 처리
            return ResponseEntity.badRequest().body(ApiResponse.builder()
                    .success(false)
                    .message("잘못된 날짜 포맷입니다. 올바른 날짜 형식: yyyy-MM-dd")
                    .build());
        } catch (Exception e) {
            // 예기치 않은 예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.builder()
                    .success(false)
                    .message("요청 처리 중 오류가 발생했습니다.")
                    .build());
        }

    }

    @GetMapping("/completed")
    public ResponseEntity<ApiResponse> findTodoCompleted() {
        try{

            List<TodoDTO> todos = todoService.getCompleted();

            // 정상적인 데이터 조회
            return ResponseEntity.ok(ApiResponse.builder()
                    .success(true)
                    .data(todos)
                    .build());

        } catch (Exception e) {
            // 예기치 않은 예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.builder()
                    .success(false)
                    .message("요청 처리 중 오류가 발생했습니다.")
                    .build());
        }

    }

    @GetMapping("/tag")
    public ResponseEntity<ApiResponse> findTodoByTag() {
        try{


            List<TodoDTO> todos = todoService.getAll();

            Map<String, List<TodoDTO>> result = new HashMap<>();

            String tag;
            for (TodoDTO todo : todos) {

                if(todo.getTag().isPresent()){
                    tag = todo.getTag().get();
                }else {
                    tag = "no_tag";
                }

                if (!result.containsKey(tag)) {
                    result.put(tag, new ArrayList<>());
                }

                result.get(tag).add(todo);
            }

            // 정상적인 데이터 조회
            return ResponseEntity.ok(ApiResponse.builder()
                    .success(true)
                    .data(result)
                    .build());

        } catch (Exception e) {
            // 예기치 않은 예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.builder()
                    .success(false)
                    .message("요청 처리 중 오류가 발생했습니다.")
                    .build());
        }

    }

    @PostMapping("")
    public ResponseEntity<ApiResponse> addTodo(@RequestBody TodoDTO todoDTO) {
        try {
            TodoDTO todos = todoService.insertTodo(todoDTO);

            // 정상적인 데이터 조회
            return ResponseEntity.ok(ApiResponse.builder()
                    .success(true)
                    .data(todos)
                    .message("새로운 투두가 추가되었습니다.")
                    .build());

        } catch (Exception e) {
            // 예기치 않은 예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.builder()
                    .success(false)
                    .message("요청 처리 중 오류가 발생했습니다.")
                    .build());
        }
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<ApiResponse> editTodoComplete(@PathVariable int id) {

        try {

            int result = todoService.completeTodo(id);
            if(result > 0){
                return ResponseEntity.ok(ApiResponse.builder()
                        .success(true)
                        .message("완료되었습니다.")
                        .build());
            }else{
                return ResponseEntity.ok(ApiResponse.builder()
                        .success(false)
                        .message("존재하지 않는 id 입니다.")
                        .build());
            }

        } catch (Exception e) {
            // 예기치 않은 예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.builder()
                    .success(false)
                    .message("요청 처리 중 오류가 발생했습니다.")
                    .build());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateTodo(@PathVariable int id, @RequestBody TodoDTO todoDTO) {
        try {
            TodoDTO todos = todoService.updateTodo(todoDTO, id);

            // 정상적인 데이터 조회
            return ResponseEntity.ok(ApiResponse.builder()
                    .success(true)
                    .message("수정되었습니다.")
                    .data(todos)
                    .build());

        } catch (Exception e) {
            // 예기치 않은 예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.builder()
                    .success(false)
                    .message("요청 처리 중 오류가 발생했습니다.")
                    .build());
        }

    }

    @PutMapping("/not_completed")
    public ResponseEntity<ApiResponse> notCompleted(@RequestBody Map<String, List<Integer>> map) {

        try {
            todoService.notCompleteTodo(map.get("ids"));

            // 정상적인 데이터 조회
            return ResponseEntity.ok(ApiResponse.builder()
                    .success(true)
                    .message("복구되었습니다.")
                    .build());

        } catch (Exception e) {
            // 예기치 않은 예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.builder()
                    .success(false)
                    .message("요청 처리 중 오류가 발생했습니다.")
                    .build());
        }

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteTodo(@PathVariable int id) {
        try {

            todoService.deleteTodo(id);

            // 정상적인 데이터 조회
            return ResponseEntity.ok(ApiResponse.builder()
                    .success(true)
                    .message("삭제되었습니다.")
                    .build());

        } catch (Exception e) {
            // 예기치 않은 예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.builder()
                    .success(false)
                    .message("요청 처리 중 오류가 발생했습니다.")
                    .build());
        }

    }

    @DeleteMapping("/many")
    public ResponseEntity<ApiResponse> deleteManyTodo(@RequestBody Map<String, List<Integer>> map) {
        try {

            todoService.deleteTodos(map.get("ids"));

            // 정상적인 데이터 조회
            return ResponseEntity.ok(ApiResponse.builder()
                    .success(true)
                    .message("삭제되었습니다.")
                    .build());

        } catch (Exception e) {
            // 예기치 않은 예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.builder()
                    .success(false)
                    .message("요청 처리 중 오류가 발생했습니다.")
                    .build());
        }

    }




}
