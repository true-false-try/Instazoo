import {Comment} from "./Comment";

export interface Post {
  /**
   * Params with last char equals ? isn't optional
   */
    id?: number;
    title: string;
    caption: string;
    location: string;
    image?: File;
    likes?: number;
    userLiked?: string[];
    comments?: Comment[];
    username?: string;

}
