<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Books</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <#--    <script src="../JavaScripts/javascript.js"></script> <!-- Include the external JavaScript file &ndash;&gt;-->
    <style>
        <#include "../css/style.css">
        <#include "../css/style2.css">
    </style>
</head>
<body>

<div class="content-area">
    <h1>Filter Books</h1>
    <h2>Return to Home page:</h2>
    <div class="nav-links">
        <a href="/home">Home</a>
    </div>
</div>
<div class="content-area">
    <label for="titleFilter">Title:</label>
    <input type="text" id="titleFilter">

    <label for="authorFilter">Author:</label>
    <input type="text" id="authorFilter">

    <label for="subjectFilter">Subject:</label>
    <input type="text" id="subjectFilter">
    <button id="filterButton">Filter</button>
    <h2>Books:</h2>
    <div id="bookList">
    </div>
</div>
<script>
    function getUserIdFromUrl() {
        var urlParts = window.location.href.split('/');
        var userId = urlParts[urlParts.length - 1];
        console.log('User ID:', userId);
        return userId;
    }

    function filterBooks() {
        var userId = getUserIdFromUrl();
        if (!userId) {
            console.error('User ID is empty.');
            return;
        }

        var titleFilter = $('#titleFilter').val();
        var authorFilter = $('#authorFilter').val();
        var subjectFilter = $('#subjectFilter').val();

        console.log('Filtering books with parameters:', {
            title: titleFilter,
            author: authorFilter,
            subject: subjectFilter
        });

        $.ajax({
            url: '/books/user/' + userId + '/filtered',
            type: 'GET',
            data: {
                title: titleFilter,
                author: authorFilter,
                subject: subjectFilter
            }
        }).done(function (data) {
            console.log('AJAX request successful. Received data:', data);
            $('#bookList').html(data);
        }).fail(function (jqXHR, textStatus, errorThrown) {
            console.error('AJAX request failed:', textStatus, errorThrown);
        });
    }

    $('#filterButton').on('click', function () {
        console.log('Filter button clicked.');
        filterBooks();
    });

    filterBooks();
</script>

</body>
</html>