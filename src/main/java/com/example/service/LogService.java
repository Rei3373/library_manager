package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Log;
import com.example.repository.LogRepository;

@Service
public class LogService {
	private final LogRepository logRepository;

	@Autowired
	public LogService(LogRepository logRepository) {
		this.logRepository = logRepository;
	}

	public Log create(Log log) {
		return logRepository.save(log);
	}

	public Optional<Log> findRetrunTarget(Integer libraryId, Integer userId) {
		return logRepository.findFirstByLibraryIdAndUserIdOrderByRentDateDesc(libraryId, userId);
	}

	public Log update(Log log) {
		return logRepository.save(log);
	}

	public List<Log> findByUserId(Integer userId) {
		return logRepository.findByUserId(userId);
	}
}