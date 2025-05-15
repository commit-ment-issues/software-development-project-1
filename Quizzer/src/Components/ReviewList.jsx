import React, { useEffect, useState } from 'react';
import { Link, useParams } from 'react-router-dom';
import { getQuizById } from '../utils/quizapi';
import { getReviewsByQuizId } from '../utils/reviewapi';
import { Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, Button } from '@mui/material';


function ReviewList() {
  const { quizId } = useParams();
  const [quiz, setQuiz] = useState(null);
  const [reviews, setReviews] = useState([]);
  const [loading, setLoading] = useState(true);
  const [openDialog, setOpenDialog] = useState(false);
  const [reviewToDelete, setReviewToDelete] = useState(null);
  const baseurl = import.meta.env.VITE_BACKEND_URL;

  useEffect(() => {
    const fetchQuizAndReviews = async () => {
      try {
        const quizData = await getQuizById(quizId);
        setQuiz(quizData);

        try {
          const quizReviews = await getReviewsByQuizId(quizId);
          setReviews(quizReviews);
        } catch (error) {
          if (error.message.includes("404")) {
            setReviews([]); // ei löytynyt arvosteluja
          } else {
            throw error;
          }
        }

        setLoading(false);
      } catch (error) {
        console.error("Data fetch error:", error);
      }
    };

    fetchQuizAndReviews();
  }, [quizId]);

  const handleDeleteReview = async () => {
    try {
      const response = await fetch(`${baseurl}/reviews/${reviewToDelete}`, {
        method: 'DELETE',
      });
      if (!response.ok) throw new Error('Failed to delete review');
      setReviews(reviews.filter((review) => review.reviewId !== reviewToDelete));
    } catch (error) {
      console.error('Error deleting review:', error);
    } finally {
      setOpenDialog(false);
      setReviewToDelete(null);
    }
  };

  const openDeleteDialog = (reviewId) => {
    setReviewToDelete(reviewId);
    setOpenDialog(true);
  }

  const closeDialog = () => {
    setOpenDialog(false);
    setReviewToDelete(null);
  }

  if (loading) return <div>Loading...</div>;

  const average = reviews.length > 0
    ? (reviews.reduce((sum, r) => sum + r.rating, 0) / reviews.length).toFixed(1)
    : "N/A";

  return (
    <div>
      <h1>Reviews of "{quiz.name}"</h1>
      <p>{average} rating average based on {reviews.length} reviews.</p>
      <Link to={`/quiz/${quizId}/addreview`} style={{ color: "#57B9FF" }}>
        Write your review
      </Link>
      <div>
        {reviews.map((review, i) => (
          <div key={i} style={{ border: '1px solid #ccc', padding: '1rem', margin: '1rem 0' }}>
            <h3>{review.reviewerNickname}</h3>
            <p>Rating: {review.rating}/5</p>
            <p>{review.reviewText}</p>
            <p><small>Written on: {new Date(review.date).toLocaleDateString()}</small></p>

            <Link
              to={`/editreview/${review.reviewId}`} style={{ color: '#57B9FF' }}>
              Edit
            </Link>
            <button
              onClick={() => openDeleteDialog(review.reviewId)}
              style={{ color: 'red', marginLeft: '10px', background: 'none', border: 'none', cursor: 'pointer' }}
            >
              Delete
            </button>
          </div>
        ))}
      </div>
      <Dialog open={openDialog} onClose={closeDialog}>
        <DialogTitle>Confirm Deletion</DialogTitle>
        <DialogContent>
          <DialogContentText>
            Are you sure you want to delete this review?
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={closeDialog} color="primary">
            Cancel
          </Button>
          <Button onClick={handleDeleteReview} color="error">
            Delete
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}

export default ReviewList;
