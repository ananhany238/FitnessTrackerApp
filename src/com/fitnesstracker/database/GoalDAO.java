package com.fitnesstracker.database;

import com.fitnesstracker.model.FitnessGoal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GoalDAO {

    private DatabaseManager dbManager;

    public GoalDAO() {
        this.dbManager = DatabaseManager.getInstance();
    }

    // Insert a new goal
    public int insertGoal(FitnessGoal goal) {
        String sql = "INSERT INTO Goals " +
                     "(GoalName, GoalDescription, GoalType, TargetValue, CurrentProgress, Unit, StartDate, IsCompleted) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, goal.getName());
            pstmt.setString(2, goal.getDescription());
            pstmt.setString(3, goal.getGoalType());
            pstmt.setDouble(4, goal.getTargetValue());
            pstmt.setDouble(5, goal.getCurrentProgress());
            pstmt.setString(6, goal.getUnit());
            pstmt.setTimestamp(7, new Timestamp(goal.getStartDate().getTime()));
            pstmt.setBoolean(8, goal.isCompleted());

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    goal.setId(id); // Set DB ID to object
                    return id;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // Delete goal by ID
    public boolean deleteGoal(int goalId) {
        String sql = "DELETE FROM Goals WHERE GoalID = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, goalId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update goal progress
    public boolean updateGoalProgress(FitnessGoal goal) {
        String sql = "UPDATE Goals SET CurrentProgress=?, IsCompleted=? WHERE GoalID=?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, goal.getCurrentProgress());
            pstmt.setBoolean(2, goal.isCompleted());
            pstmt.setInt(3, goal.getId());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get all goals
    public List<FitnessGoal> getAllGoals() {
        List<FitnessGoal> goals = new ArrayList<>();
        String sql = "SELECT * FROM Goals ORDER BY StartDate DESC";
        try (Connection conn = dbManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                FitnessGoal goal = new FitnessGoal(
                        rs.getString("GoalName"),
                        rs.getString("GoalDescription"),
                        rs.getString("GoalType"),
                        rs.getDouble("TargetValue"),
                        rs.getString("Unit")
                );
                goal.setCurrentProgress(rs.getDouble("CurrentProgress"));
                goal.setCompleted(rs.getBoolean("IsCompleted"));
                goal.setId(rs.getInt("GoalID"));
                Timestamp startTs = rs.getTimestamp("StartDate");
                if (startTs != null) goal.setTargetDate(new java.util.Date(startTs.getTime()));
                goals.add(goal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return goals;
    }
}
