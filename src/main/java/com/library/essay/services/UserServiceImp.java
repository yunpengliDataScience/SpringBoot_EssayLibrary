package com.library.essay.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.library.essay.persistence.entities.User;
import com.library.essay.persistence.repositories.UserRepository;

@Service(value = "userService")
@Transactional
public class UserServiceImp implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public User getUser(String userName) {
    return userRepository.findByUserName(userName);
  }

  @Override
  public User authenticate(String userName, String password) {

    return userRepository.findByUserNameAndPassword(userName, password);
  }

  @Override
  public List<User> getUsers() {
    return userRepository.findAll();
  }

  @Override
  public User saveOrUpdate(User user) {
    return userRepository.save(user);
  }

  @Override
  public void delete(User user) {
    userRepository.delete(user);
  }

  @Override
  public void deleteAll() {
    userRepository.deleteAll();
  }

}
