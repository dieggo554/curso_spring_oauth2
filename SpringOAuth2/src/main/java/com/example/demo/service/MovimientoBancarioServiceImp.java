package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.IMovimientoBancarioDao;
import com.example.demo.entity.MovimientoBancario;

@Service
public class MovimientoBancarioServiceImp implements IMovimientoBancarioService {

	@Autowired
	IMovimientoBancarioDao dao;
	
	@Override
	@Transactional(readOnly = true)
	public List<MovimientoBancario> findAll() {
		return (List<MovimientoBancario>) dao.findAll();
	}

	@Override
	@Transactional
	public void save(MovimientoBancario movimientoBancario) {
		dao.save(movimientoBancario);
	}

	@Override
	@Transactional(readOnly = true)
	public List<MovimientoBancario> getMovimientoBancarioUser(Long id) {
		return (List<MovimientoBancario>) dao.findByUserId(id);
	}
}
