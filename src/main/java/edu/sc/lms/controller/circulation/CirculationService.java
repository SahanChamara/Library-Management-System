package edu.sc.lms.controller.circulation;
import java.util.List;

public interface CirculationService {
    List<String> loadMemberNames();
    List<String> loadBookTitle();
}
