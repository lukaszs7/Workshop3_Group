package com.coderslab.workshop.dao;

import com.coderslab.workshop.model.Exercise;
import com.coderslab.workshop.model.Solution;
import com.coderslab.workshop.model.User;
import com.coderslab.workshop.utils.DbUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SolutionDao {
    private UserDao userDao = new UserDao();

    public List<Solution> findRecent(int limit) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement("select * from solutions " +
                             "JOIN users on solutions.USER_ID = users.ID " +
                             "JOIN exercises on solutions.EXERCISE_ID = exercises.ID " +
                             "order by CREATED_AT DESC " +
                             "LIMIT ?")) {

            preparedStatement.setInt(1, limit);
            List<Solution> solutions = new ArrayList<>();

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("solutions.id");
                LocalDateTime updated_at = convertTimestamp(resultSet.getTimestamp("solutions.updated_at"));
                LocalDateTime created_at = convertTimestamp(resultSet.getTimestamp("solutions.created_at"));
                String description = resultSet.getString("solutions.description");

                // user
                int userId = resultSet.getInt("users.id");
                String username = resultSet.getString("users.username");
                String password = resultSet.getString("users.password");
                String email = resultSet.getString("users.email");

                // exercise
                int exerciseId = resultSet.getInt("exercises.id");
                String exerciseTitle = resultSet.getString("exercises.title");
                String exerciseDesc = resultSet.getString("exercises.description");

                solutions.add(new Solution(id, created_at, updated_at, description, new User(userId, username, password, email), new Exercise(exerciseId, exerciseTitle, exerciseDesc)));
            }
            return solutions;
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }


    private LocalDateTime convertTimestamp(Timestamp timestamp) {
        return timestamp == null ? null : timestamp.toLocalDateTime();
    }
}
