export interface Booking {
  showId: number;
  movieId: number;
  totalAmount: number;
  seatIds: number[];
}

export interface Payment {
  bookingId: number;
  amount: number;
  atm: string;
  pin: string;
}
