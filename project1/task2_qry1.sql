USE test;

SELECT 	DISTINCT *
FROM movies, stars_in_movies, stars
WHERE movies.id = stars_in_movies.movie_id
	AND	star_id = stars.id
    AND stars.first_name = "Jennifer"
    AND stars.last_name = "Lopez"
ORDER BY stars.first_name ASC;