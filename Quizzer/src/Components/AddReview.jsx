import { useState, useEffect } from "react";
import { DialogActions, DialogContent, DialogTitle, TextField, Snackbar, Alert, Button, FormControl, FormLabel, RadioGroup, FormControlLabel, Radio, } from '@mui/material';
import { useParams } from "react-router-dom";
import { getQuizById } from "../utils/quizapi";
import { saveReview } from "../utils/reviewapi";

export default function AddReview() {

    const { quizId } = useParams();
    const [quiz, setQuiz] = useState(null);

    useEffect(() => {
        const fetchQuiz = async () => {
            try {
                const quizData = await getQuizById(quizId);
                setQuiz(quizData);
            } catch (error) {
                console.error("Error fetching quiz:", error);
            }
        };
        fetchQuiz();
    }, [quizId]);

    const [review, setReview] = useState({
        reviewerNickname: "",
        rating: 0,
        reviewText: "",
    });

    const [open, setOpen] = useState(false);
    const [snackbarOpen, setSnackbarOpen] = useState(false);

    const handleClickOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);
    const handleSnackbarClose = () => setSnackbarOpen(false);

    const handleChange = (e) => {
        setReview({
            ...review,
            [e.target.name]: e.target.value,
        });
    };

    const handleRatingChange = (event) => {
        setReview({
            ...review,
            rating: Number(event.target.value),
        });
    };

    const handleAddReview = async () => {
        if (!review.reviewerNickname || !review.rating || !review.reviewText) {
            alert("Please fill in all fields");
            return;
        }

        try {
            await saveReview(quizId, review);
            setSnackbarOpen(true);

            setReview({
                reviewerNickname: "",
                rating: 0,
                reviewText: "",
            });

            handleClose();
        } catch (error) {
            console.error("Error saving review:", error);
        }
    };

    return (
        <div>
            <DialogTitle>
                Add a review for "{quiz?.name || 'Unknown Quiz'}"
            </DialogTitle>
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
                    onChange={handleChange}
                />
            </DialogContent>

            <DialogActions>
                <Button onClick={handleAddReview} color="primary">
                    Submit your review
                </Button>
            </DialogActions>

            <Snackbar
                open={snackbarOpen}
                autoHideDuration={3000}
                onClose={handleSnackbarClose}
            >
                <Alert onClose={handleSnackbarClose} severity="success">
                    Review submitted!
                </Alert>
            </Snackbar>
        </div>
    );
}
