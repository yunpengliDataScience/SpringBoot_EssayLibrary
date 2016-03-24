package com.library.essay.utils.search;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.library.essay.persistence.entities.Essay;

public final class EssaySearchSpecifications {

	private EssaySearchSpecifications() {
	}

	private static String getContainsLikePattern(String searchTerm) {
		if (searchTerm == null || "".equals(searchTerm) || searchTerm.isEmpty()) {
			return "%";
		} else {
			return "%" + searchTerm.toLowerCase() + "%";
		}
	}

	public static Specification<Essay> titleContainsIgnoreCase(String searchTerm) {

		return new Specification<Essay>() {
			@Override
			public Predicate toPredicate(Root<Essay> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// Create the query here.

				String containsLikePattern = getContainsLikePattern(searchTerm);
				return cb.like(cb.lower(root.<String> get("title")), containsLikePattern);
			}
		};
	}

	public static Specification<Essay> authorContainsIgnoreCase(String searchTerm) {

		return new Specification<Essay>() {
			@Override
			public Predicate toPredicate(Root<Essay> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// Create the query here.

				String containsLikePattern = getContainsLikePattern(searchTerm);
				return cb.like(cb.lower(root.<String> get("author")), containsLikePattern);
			}
		};
	}

	public static Specification<Essay> contentContainsIgnoreCase(String searchTerm) {

		return new Specification<Essay>() {
			@Override
			public Predicate toPredicate(Root<Essay> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// Create the query here.

				String containsLikePattern = getContainsLikePattern(searchTerm);
				return cb.like(cb.lower(root.<String> get("content")), containsLikePattern);
			}
		};
	}
}
