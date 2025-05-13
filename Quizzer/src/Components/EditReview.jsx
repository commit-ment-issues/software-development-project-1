import { useEffect, useState } from 'react';
import { Container, DialogTitle, DialogContent, TextField, FormControl,
  FormLabel, RadioGroup, FormControlLabel, Radio, Button } from '@mui/material';
import { useParams, useNavigate } from 'react-router-dom';

const baseurl = import.meta.env.VITE_BACKEND_URL

function EditReview() {

  const { reviewId } = useParams();
  const navigate = useNavigate();
  const [review, setReview] = useState({
    reviewerNickname: '',
    rating: 1,
    reviewText: ''
  });

  useEffect(() => {
    fetch(`${baseurl}/reviews/${reviewId}`)
      .then((res) => {
        if (!res.ok) throw new Error('Failed to fetch review');
        return res.json();
      })
      .then((data) => {
        setReview(data);
      })
      .catch((err) => {
        console.log(err.message);
      });
  }, [reviewId]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setReview({ ...review, [name]: value });
  };

  const handleRatingChange = (e) => {
    setReview({ ...review, rating: Number(e.target.value) });
  };

  const handleUpdateReview = () => {
    fetch(`${baseurl}/reviews/edit/${reviewId}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(review)
    })
      .then((res) => {
        if (!res.ok) throw new Error('Failed to update review');
        return res.json();
      })
      .then(() => {
        navigate(-1);
      })
      .catch((err) => {
        console.log(err.message);
      });
  };

  return (
    <Container>
      <DialogTitle>Edit your review</DialogTitle>

      <DialogContent>
        <TextField
          required
          autoFocus
          margin="dense"
          name="reviewerNickname"
          label="Nickname"
          value={review.reviewerNickname}
          type="text"
          fullWidth
          onChange={handleChange}
        />

        <FormControl component="fieldset" sx={{ mt: 2 }}>
          <FormLabel component="legend">Rating *</FormLabel>
          <RadioGroup
            name="rating"
            value={String(review.rating)}
            onChange={handleRatingChange}
          >
            <FormControlLabel value="1" control={<Radio />} label="1 - Useless" />
            <FormControlLabel value="2" control={<Radio />} label="2 - Poor" />
            <FormControlLabel value="3" control={<Radio />} label="3 - Ok" />
            <FormControlLabel value="4" control={<Radio />} label="4 - Good" />
            <FormControlLabel value="5" control={<Radio />} label="5 - Excellent" />
          </RadioGroup>
        </FormControl>

        <TextField
          required
          margin="dense"
          name="reviewText"
          label="Review"
          value={review.reviewText}
          type="text"
          fullWidth
          multiline
          rows={4}
          onChange={handleChange}
        />
      </DialogContent>

        <Button onClick={handleUpdateReview} variant="contained" color="primary">
          Save Changes
        </Button>
    </Container>
  );
}

export default EditReview;
