export interface Booking {
  showId: number;
  userId: number;
  totalAmount: number;
  seatIds: number[];
}

export interface Order {
  id: number;
  movie: string;
  customer: string;
  seats: string[];
  date: string;
  total: number;
  status: string;
}
