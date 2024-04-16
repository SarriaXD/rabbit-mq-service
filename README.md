## Video Processing Workflow With AWS S3 and RabbitMQ

1. **Accept RabbitMQ Message**
   - Receive and acknowledge the message from RabbitMQ that includes the video ID or reference.

2. **Database Lookup**
   - Query the `post` table in the database to retrieve the original URL associated with the video ID.

3. **Download Video from AWS S3**
   - Use the original URL to download the video file from Amazon S3 storage.

4. **Transform Video to HLS Format**
   - Utilize a video processing tool (e.g., FFmpeg) to convert the video into the HLS (HTTP Live Streaming) format, which typically includes generating `.m3u8` playlists and `.ts` video segment files.

5. **Upload HLS Folder to AWS S3**
   - Upload the generated HLS folder containing the `.m3u8` file(s) and `.ts` segment(s) back to a specified bucket in Amazon S3.

6. **Database Update**
   - Update the `post` table in the database with the new URL pointing to the HLS folder on AWS S3.

## Database Table: `post`

| Name            | Type    | Length | Decimals | Not Null | Key | Comment  |
|-----------------|---------|--------|----------|----------|-----|----------|
| id              | int4    | 32     | 0        | ✔        | 🔑   |          |
| user_id         | int4    | 32     | 0        | ✔        |     |          |
| original_video_ | varchar | 0      | 0        |          |     |          |
| final_video_url | varchar | 0      | 0        |          |     |          |

