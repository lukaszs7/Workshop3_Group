package com.coderslab.workshop.controller;

import com.coderslab.workshop.dao.SolutionDao;
import com.coderslab.workshop.model.Solution;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/solutions/*")
public class SolutionsController extends HttpServlet {
    private static final String NUMBER_OF_SOLUTIONS_KEY = "number-solutions";
    private SolutionDao solutionDao = new SolutionDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        System.out.println("Path info = " + pathInfo);
        Integer solutionId = getPathId(pathInfo);
        if (solutionId == null) {
            int numberOfSolutions = Integer.parseInt(getServletContext().getInitParameter(NUMBER_OF_SOLUTIONS_KEY));
            List<Solution> solutions = solutionDao.findRecent(numberOfSolutions);
            req.setAttribute("solutions", solutions);

            getServletContext().getRequestDispatcher("/views/solutions.jsp")
                    .forward(req, resp);
        } else {
            System.out.println("Wyswietl detailsy dla solution = " + solutionId);
        }
    }

    private Integer getPathId(String pathInfo) {
        if (pathInfo == null) {
            return null;
        }

        String[] split = pathInfo.split("/");
        return Integer.parseInt(split[1]);
    }
}
