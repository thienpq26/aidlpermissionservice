package com.example.aidlpermission;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.RemoteException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StudentService extends Service {
    public StudentService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        if (checkSelfPermission("com.example.aidlpermission.ACCESS_STUDENT_SERVICE") == PackageManager.PERMISSION_GRANTED)
            return mBinder;
        else return null;
    }

    private IStudentService.Stub mBinder = new IStudentService.Stub() {

        List<Student> students = Collections.synchronizedList(new ArrayList<Student>());

        @Override
        public void addStudent(int id, String name, String phone, String address, String email) throws RemoteException {
            students.add(new Student(id, name, phone, address, email));
        }

        @Override
        public List<Student> getStudents() throws RemoteException {
            return students;
        }

        @Override
        public Student getStudent(int id) throws RemoteException {
            for (Student student : students) {
                if (student.getId() == id) {
                    return student;
                }
            }
            return null;
        }
    };
}
