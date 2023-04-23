package br.com.devdojo.examgenerator.persistence.model;

public class Course extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	private String name;

	private Professor professor;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	@Override
	public String toString() {
		return "Course [name=" + name + ", professor=" + professor + "]";
	}

}
